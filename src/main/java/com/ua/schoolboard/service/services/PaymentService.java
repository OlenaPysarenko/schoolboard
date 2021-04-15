package com.ua.schoolboard.service.services;

import com.ua.schoolboard.exceptions.CustomException;
import com.ua.schoolboard.persistence.repos.PaymentRepository;
import com.ua.schoolboard.persistence.repos.UserRepository;
import com.ua.schoolboard.rest.model.*;
import com.ua.schoolboard.service.mappers.PaymentMapper;
import com.ua.schoolboard.service.mappers.UserMapper;
import com.ua.schoolboard.service.model.PaymentBO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.*;

@Component
@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final UserRepository userRepository;
    private final GroupService groupService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final ClassService classService;


    @Transactional
    public Long register(PaymentTO paymentTO) throws CustomException {
        PaymentBO payment = paymentRepository.register(paymentMapper.toPaymentBO(paymentTO));
        return payment.getPaymentId();
    }

    public void calculateAmount(PaymentTO paymentTO) throws CustomException {
        UserTO userFrom = paymentTO.getUserFrom();
        Role role = userFrom.getRole();
        if (role.equals(Role.STUDENT)) {
            UpdateStudentTO student = userService.getStudent(userFrom.getUserId());
            calculateClassesCovered(paymentTO.getAmount(), student);
            UpdateAdminTO admin = userService.getAdmin(paymentTO.getUserTo().getUserId());
            Integer amount = admin.getBalance().getAmount();
            admin.getBalance().setAmount(amount + paymentTO.getAmount());
            userRepository.updateUser(userMapper.toUserBO(admin));
        } else {
            Long userId = paymentTO.getUserFrom().getUserId();
            UpdateAdminTO admin = userService.getAdmin(userId);
            Integer amount = admin.getBalance().getAmount();
            admin.getBalance().setAmount(amount - paymentTO.getAmount());
            UpdateTeacherTO teacher = (UpdateTeacherTO) paymentTO.getUserTo();
            //is set every month = salary
            teacher.getBalance().setAmount(paymentTO.getAmount());
            userRepository.updateUser(userMapper.toUpdateTeacherBO(teacher));
        }
    }

    public void calculateClassesCovered(Integer sum, UpdateStudentTO student) throws CustomException {
        int classesCovered = 0;
        List<GroupTO> all = groupService.getAll();
        for (GroupTO group : all) {
            if (group.getStudents().contains(student)) {
                classesCovered = student.getRates().iterator().next().calculateCovered(sum, group.getGroupType());
                student.updateBalance(sum, classesCovered);
                userRepository.updateUser(userMapper.toUserBO(student));
            }

        }
    }

    public PaymentTO getById(Long paymentId) {
        PaymentBO payment = paymentRepository.getPaymentById(paymentId);
        return paymentMapper.toPaymentTO(payment);
    }

    private Integer getSalaryForTeacher(long teacherId) throws CustomException {
        UpdateTeacherTO teacher = userService.getTeacher(teacherId);
        return calcWages(groupService.listByTeacher(teacher.getUserId()));
    }

    public void updateBalances(long adminId, long teacherId) throws CustomException {
        UpdateAdminTO admin = userService.getAdmin(adminId);
        UpdateTeacherTO teacher = userService.getTeacher(teacherId);
        Integer salaryForTeacher = getSalaryForTeacher(teacherId);
        teacher.updateBalance(salaryForTeacher);
        userRepository.updateUser(userMapper.toUpdateTeacherBO(teacher));

    }

    private Integer calcWages(List<GroupTO> listOfGroups) throws CustomException {
        //q-ty of classes by Teacher for previous month
        int sum = 0;
        for (GroupTO listOfGroup : listOfGroups) {
            Integer i = calcWagePerGroup(listOfGroup);
            int i1 = i;
            sum += i1;
        }
        return sum;
    }

    private Integer calcWagePerGroup(GroupTO g) throws CustomException {
        return classService.findAllByGroup(g).stream()
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

