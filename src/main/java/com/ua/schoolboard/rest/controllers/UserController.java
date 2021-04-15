package com.ua.schoolboard.rest.controllers;

import com.ua.schoolboard.exceptions.CustomException;
import com.ua.schoolboard.rest.model.*;
import com.ua.schoolboard.service.services.CustomExceptionResolver;
import com.ua.schoolboard.service.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final CustomExceptionResolver resolver;

    @GetMapping("/studentEdit/{userId}")
    public String studentEditForm(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("userId", userId);
        model.addAttribute("roles", Role.values());
        try {
            model.addAttribute("updUser", userService.getStudent(userId));
        } catch (CustomException e) {
            resolver.resolveError(model, "main", e);
        }
        model.addAttribute("languages", Language.values());
        return "/studentEdit";
    }

    @PostMapping("/studentEdit")
    public String updateStudentProfile(UpdateStudentTO updUser, Model model) {
        UserTO user = null;
        try {
            user = userService.updateStudent(updUser);
        } catch (CustomException e) {
           // resolver.resolveError(model, "main", e);
            e.printStackTrace();
        }
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        model.addAttribute("languages", Language.values());
        return "main";

    }

    @GetMapping("/teacherEdit/{userId}")
    public String teacherEditForm(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("roles", Role.values());
        try {
            model.addAttribute("updUser", userService.getTeacher(userId));
        } catch (CustomException e) {
            resolver.resolveError(model, "main", e);
        }
        model.addAttribute("languages", Language.values());
        model.addAttribute("userId", userId);
        return "teacherEdit";
    }

    @PostMapping("teacherEdit/")
    public String updateTeacherProfile(UpdateTeacherTO updUser, Model model) {
        UserTO user = null;
        try {
            user = userService.updateTeacher(updUser);
        } catch (CustomException e) {
            resolver.resolveError(model, "main", e);
        }
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        model.addAttribute("languages", Language.values());
        return "main";
    }

    @GetMapping("adminEdit/{userId}")
    public String adminEditForm(@PathVariable("userId") Long userId, UpdateTeacherTO updUser, Model model) {
        model.addAttribute("roles", Role.values());
        model.addAttribute("updUser", updUser);
        model.addAttribute("languages", Language.values());
        model.addAttribute("userId", userId);
        return "adminEdit";
    }

    @PostMapping("adminEdit")
    public String updateAdminProfile(UpdateAdminTO updUser, Model model) {
        UserTO user = null;
        try {
            user = userService.updateAdmin(updUser);
        } catch (CustomException e) {
            resolver.resolveError(model, "main", e);
        }
        model.addAttribute("roles", Role.values());
        model.addAttribute("user", user);
        model.addAttribute("languages", Language.values());
        return "main";
    }

    @GetMapping("disable/{userId}")
    public String disableUser(@PathVariable("userId") long userId, Model model) {
        try {
            userService.disableUser(userId);
        } catch (CustomException e) {
            resolver.resolveError(model, "main", e);
        }
        return "main";
    }

}
