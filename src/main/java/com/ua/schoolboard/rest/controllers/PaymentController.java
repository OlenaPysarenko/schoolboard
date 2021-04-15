package com.ua.schoolboard.rest.controllers;

import com.ua.schoolboard.exceptions.CustomException;
import com.ua.schoolboard.rest.model.*;
import com.ua.schoolboard.service.services.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PaymentController {
    private final UserService userService;
    private final PaymentService paymentService;
    private final CustomExceptionResolver resolver;


    @GetMapping("adminRoom/{userId}")
    public String getAdminPage(@PathVariable("userId") long userId, Model model) {
        try {
            model.addAttribute("user", userService.findById(userId));
        } catch (CustomException e) {
            resolver.resolveError(model, "main", e);
        }
        return "adminRoom";
    }

    @GetMapping("newPayment/{userId}")
    public String getPayment(@PathVariable("userId") long userId, Model model) throws CustomException {
        try {
            model.addAttribute("user", userService.findById(userId));
        } catch (CustomException e) {
            resolver.resolveError(model, "main", e);
        }
        model.addAttribute("usersFrom", userService.getAllStudents());
        //model.addAttribute("userTo", userService.getAdmin(userId));
        return "payment";
    }

    @PostMapping("newPayment/{userId}")
    public String createPayment(@PathVariable("userId") long userId, Long userFrom, Integer sum, Model model) {
        UpdateStudentTO student = null;
        PaymentTO payment = new PaymentTO();
        Date date = new Date();
        payment.setAmount(sum);
        payment.setDate(date);
        try {
            student = userService.getStudent(userFrom);
            payment.setUserFrom(student);
            payment.setUserTo(userService.findById(userId));
            long paymentId = paymentService.register(payment);
            PaymentTO byId = paymentService.getById(paymentId);
            paymentService.calculateAmount(byId);
            model.addAttribute("user", userService.findById(userId));
            model.addAttribute("student", userService.getStudent(userFrom));
        } catch (CustomException | NullPointerException e) {
            //resolver.resolveError(model, "adminRoom", e);
            e.printStackTrace();
        }
        model.addAttribute("students", userService.getAllStudents());
        return "payment";
    }

    @GetMapping("newSalaryPayment/{userId}")
    public String getSalaryPayment(@PathVariable("userId") long userId, Model model) throws CustomException {
        try {
            model.addAttribute("user", userService.findById(userId));
        } catch (CustomException e) {
            resolver.resolveError(model, "main", e);
        }
        model.addAttribute("usersFrom", userService.getAllStudents());
        return "teacherPayment";
    }

    @PostMapping("newSalaryPayment/{userId}")
    public String createSalaryPayment(@PathVariable("userId") long userId, Long userFrom, Integer sum, Model model) {
        UpdateStudentTO student = null;
        PaymentTO payment = new PaymentTO();
        Date date = new Date();
        payment.setAmount(sum);
        payment.setDate(date);
        try {
            student = userService.getStudent(userFrom);
            payment.setUserFrom(student);
            payment.setUserTo(userService.findById(userId));
            long paymentId = paymentService.register(payment);
            PaymentTO byId = paymentService.getById(paymentId);
            paymentService.calculateAmount(byId);
            model.addAttribute("user", userService.findById(userId));
            model.addAttribute("student", userService.getStudent(userFrom));
        } catch (CustomException e) {
            resolver.resolveError(model, "adminRoom", e);
            e.printStackTrace();
        }
        model.addAttribute("students", userService.getAllStudents());
        return "teacherPayment";
    }



}
