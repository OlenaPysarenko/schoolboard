package com.ua.schoolboard.rest.controllers;

import com.ua.schoolboard.rest.model.*;
import com.ua.schoolboard.service.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/studentEdit/{userId}")
    public String studentEditForm(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("userId", userId);
        model.addAttribute("roles", Role.values());
        model.addAttribute("updUser", userService.getStudent(userId));
        model.addAttribute("languages", Language.values());
        return "/studentEdit";
    }

    @PostMapping("/studentEdit")
    public String updateStudentProfile(UpdateStudentTO updUser, Model model) {
        UserTO user = userService.updateStudent(updUser);
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        model.addAttribute("languages", Language.values());
        return "main";

    }

    @GetMapping("/teacherEdit/{userId}")
    public String teacherEditForm(@PathVariable("userId") Long userId,  Model model) {
        model.addAttribute("roles", Role.values());
        model.addAttribute("updUser", userService.getTeacher(userId));
        model.addAttribute("languages", Language.values());
        model.addAttribute("userId", userId);
        return "teacherEdit";
    }

    @PostMapping("teacherEdit/")
    public String updateTeacherProfile( UpdateTeacherTO updUser, Model model) {
        UserTO user = userService.updateTeacher(updUser);
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
        UserTO user = userService.updateAdmin(updUser);
        model.addAttribute("roles", Role.values());
        model.addAttribute("user",user);
        model.addAttribute("languages", Language.values());
        //userService.updateAdmin(updUser);
        return "main";
    }

    @GetMapping("disable/{userId}")
    public String disableUser(@PathVariable("userId") long userId) {
        userService.disableUser(userId);
        return "main";
    }

    @GetMapping("delete/{userId}")
    public String deleteUser(@PathVariable("userId") long userId) {
        userService.deleteUser(userId);
        return "main";
    }
}
