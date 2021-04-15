package com.ua.schoolboard.rest.controllers;

import com.ua.schoolboard.exceptions.CustomException;
import com.ua.schoolboard.rest.model.Role;
import com.ua.schoolboard.rest.model.UserTO;
import com.ua.schoolboard.service.services.CustomExceptionResolver;
import com.ua.schoolboard.service.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;
    private final CustomExceptionResolver resolver;

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @PostMapping("/login")
    public String loginUser(UserTO user, Model model)  {
       try {
            userService.login(user.getEmail(), user.getPassword());
            UserTO loggedInUser = userService.findByEmail(user.getEmail());
            model.addAttribute("user", loggedInUser);
        } catch (CustomException e) {
            return resolver.resolveError(model, "login", e);
        }
        model.addAttribute("roles", Role.values());
        model.addAttribute("teacher", Role.TEACHER);
        model.addAttribute("student", Role.STUDENT);
        model.addAttribute("admin", Role.ADMIN);
        return "main";
    }
}
