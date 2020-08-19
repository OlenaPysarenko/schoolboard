package com.ua.schoolboard.rest.controllers;

import com.ua.schoolboard.rest.model.Role;
import com.ua.schoolboard.rest.model.UserTO;
import com.ua.schoolboard.service.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.transform.Result;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    // @RolesAllowed({"ADMIN"})
    @GetMapping("/registration")
    public String registerUser(Model model) {
        model.addAttribute("roles", Role.values());
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@Valid UserTO user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "registration";
        }

        userService.register(user);
       // model.addAttribute("users", userService.getAll());
        return "login";
    }
}
