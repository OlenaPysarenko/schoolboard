package com.ua.schoolboard.rest.controllers;

import com.ua.schoolboard.persistence.Role;
import com.ua.schoolboard.persistence.repos.UserRepository;
import com.ua.schoolboard.rest.model.UserTO;
import com.ua.schoolboard.service.mappers.UserMapper;
import com.ua.schoolboard.service.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserService userService;

    @RolesAllowed({"ADMIN"})
    @GetMapping("/registration")
    public String registerUser(UserTO user, Model model) {
        userService.register(user);
        model.addAttribute("users", userService.getAll());
        model.addAttribute("roles", Role.values());
        return "registration";
    }

    @PostMapping("/registration")
    //@ResponseStatus(HttpStatus.CREATED)
    public String login(String email, String password) {
        userService.login(email, password);

        return null;
    }
}
