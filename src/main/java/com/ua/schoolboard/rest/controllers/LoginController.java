package com.ua.schoolboard.rest.controllers;

import com.ua.schoolboard.exceptions.ExceptionUtil;
import com.ua.schoolboard.persistence.repos.UserRepo;
import com.ua.schoolboard.rest.model.Role;
import com.ua.schoolboard.rest.model.UserTO;
import com.ua.schoolboard.service.mappers.UserMapper;
import com.ua.schoolboard.service.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;


import static com.ua.schoolboard.exceptions.ErrorCode.*;
import static com.ua.schoolboard.exceptions.ExceptionUtil.getException;
import static com.ua.schoolboard.exceptions.ExceptionUtil.getSupplierException;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @PostMapping("/login")
    public String loginUser(UserTO user, Model model) {
        userService.login(user.getEmail(), user.getPassword());
        UserTO loggedInUser = userService.findByEmail(user.getEmail());
        //Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
        ExceptionUtil.getException(INVALID_CREDENTIAL);
        model.addAttribute("user", loggedInUser);
        model.addAttribute("roles", Role.values());
        model.addAttribute("teacher", Role.TEACHER);
        model.addAttribute("student", Role.STUDENT);
        model.addAttribute("admin", Role.ADMIN);
        return "main";
    }
}
