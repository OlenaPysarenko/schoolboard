package com.ua.schoolboard.service.services;

import com.ua.schoolboard.persistence.repos.PaymentRepository;
import com.ua.schoolboard.persistence.repos.UserRepository;
import com.ua.schoolboard.rest.model.*;
import com.ua.schoolboard.service.mappers.PaymentMapper;
import com.ua.schoolboard.service.mappers.UserMapper;
import com.ua.schoolboard.service.model.PaymentBO;
import com.ua.schoolboard.service.model.RatesBO;
import com.ua.schoolboard.service.model.UserBO;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.interceptor.JamonPerformanceMonitorInterceptor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final UserRepository userRepository;
    private final GroupService groupService;
    private final RatesService ratesService;
    private final UserService userService;
    private final UserMapper userMapper;


    @Transactional
    public void register(PaymentTO paymentTO, long userId) {
        PaymentBO payment = paymentRepository.register(paymentMapper.toPaymentBO(paymentTO));
        UserBO user = userRepository.findUserById(userId);
        user.getBalance().getPayments().add(payment);
        userRepository.updateUser(user);
    }

    //todo make calculate classes covered
    public void assignPaymentToStudent(Integer sum, UpdateStudentTO student) {
        int classesCovered = 0;
        List<GroupTO> all = groupService.getAll();
        //GroupTO group = all.stream().filter(g -> g.getStudents().contains(student)).collect(Collectors.toList()).iterator().next();
        for (GroupTO group : all) {
            if (group.getStudents().contains(student)) {
                //RatesTO rates = ratesService.getByRoleAndLang(Role.STUDENT, group.getLanguage());
                //RatesTO rates = student.getRates().iterator().next();
                classesCovered = student.getRates().iterator().next().calculateCovered(sum, group.getGroupType());
                student.updateBalance(sum, classesCovered);
                userRepository.updateUser(userMapper.toUserBO(student));
            }

        }
    }

    public void assignPaymentToAdmin(Integer sum, PaymentTO payment) {
        List<UserBO> allByRole = userRepository.findAllByRole(Role.ADMIN);
        allByRole.forEach(u -> u.getBalance().getPayments().add(paymentMapper.toPaymentBO(payment)));
        for (UserBO u : allByRole) {
            Integer amount = u.getBalance().getAmount();
            u.getBalance().setAmount(amount + sum);
            u.getBalance().getPayments().add(paymentMapper.toPaymentBO(payment));
        }
    }


    private Integer getSalaryForTeacher(long teacherId) {
        UpdateTeacherTO teacher = userService.getTeacher(teacherId);
        return calcWages(groupService.listByTeacher(teacher.getUserId()));
    }

    //Payment was moved inside Balance
    public void updateBalances(long adminId, long teacherId) {
        UpdateAdminTO admin = userService.getAdmin(adminId);
        UpdateTeacherTO teacher = userService.getTeacher(teacherId);
        Integer salaryForTeacher = getSalaryForTeacher(teacherId);
        addPaymentTO(admin, teacher, salaryForTeacher);
    }


    private void addPaymentTO(UpdateAdminTO admin, UpdateTeacherTO teacher, Integer salaryForTeacher) {
        //sets payment(salary) to both admin & teacher
        PaymentTO payment = teacher.getBalance().createPayment(admin, salaryForTeacher);
        teacher.updateBalance(salaryForTeacher);
        teacher.getBalance().getPayments().add(payment);
        admin.updateBalance(salaryForTeacher);
        admin.getBalance().getPayments().add(payment);
    }

    private Integer calcWages(List<GroupTO> listOfGroups) {
        //q-ty of classes by Teacher for previous month
        return listOfGroups.stream()
                .map(this::calcWagePerGroup)
                .mapToInt(i -> i)
                .sum();
    }

    private Integer calcWagePerGroup(GroupTO g) {
        return g.getClassesCovered().stream()
                .filter(c -> c.getClassDate().after(forDate(true)) && c.getClassDate().before(forDate(false)))
                .map(ClassSessionTO::calculateWageForClass)
                .mapToInt(i -> i)
                .sum();
    }

    private Date forDate(boolean startDate) {
        Date date = new Date();
        date.setMonth(date.getMonth() - (startDate
                ? 2
                : 1));
        date.setDate(LocalDate.now().minusMonths(startDate
                ? 2
                : 1).lengthOfMonth());
        return date;
    }
}

