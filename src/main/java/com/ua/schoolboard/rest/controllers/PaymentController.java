package com.ua.schoolboard.rest.controllers;

import com.ua.schoolboard.rest.model.*;
import com.ua.schoolboard.service.mappers.UserMapper;
import com.ua.schoolboard.service.services.GroupService;
import com.ua.schoolboard.service.services.PaymentService;
import com.ua.schoolboard.service.services.RatesService;
import com.ua.schoolboard.service.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PaymentController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final PaymentService paymentService;
    private final RatesService ratesService;
    private final GroupService groupService;

    @GetMapping("adminRoom/{userId}")
    public String getAdminPage(@PathVariable("userId") long userId, Model model) {
        model.addAttribute("user", userService.findById(userId));
        return "adminRoom";
    }

    @GetMapping("newPayment/{userId}")
    public String getPayment(@PathVariable("userId") long userId, Model model) {
        model.addAttribute("user", userService.findById(userId));
        model.addAttribute("students", userService.getAllStudents());
        return "payment";
    }

    @PostMapping("newPayment/{userId}")
    public String createPayment(@PathVariable("userId") long userId, Long studentId, Integer sum, Model model) {
        UpdateStudentTO student = userService.getStudent(studentId);
        PaymentTO payment = new PaymentTO();
        Date date = new Date();
        payment.setUser(student);
        payment.setAmount(sum);
        payment.setDate(date);
        paymentService.register(payment, studentId);
        paymentService.assignPaymentToStudent(sum, student);
        paymentService.assignPaymentToAdmin(sum,payment);
        model.addAttribute("user", userService.findById(userId));
        model.addAttribute("student", userService.getStudent(studentId));
        model.addAttribute("students", userService.getAllStudents());
        return "payment";
    }



}
