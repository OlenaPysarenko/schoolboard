package com.ua.schoolboard.rest.controllers;

import com.ua.schoolboard.rest.model.*;
import com.ua.schoolboard.service.services.ManageUsersService;
import com.ua.schoolboard.service.services.RatesService;
import com.ua.schoolboard.service.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class RatesController {
    private final RatesService ratesService;
    private final UserService userService;
    private final ManageUsersService manageUsersService;

    @RolesAllowed({"ADMIN"})
    @GetMapping("/rates/{userId}")
    public String registerRates(@PathVariable("userId") long userId, RatesTO rates, Model model) {
        model.addAttribute("roles", Role.values());
        model.addAttribute("rates", rates);
        model.addAttribute("languages", Language.values());
        model.addAttribute("user", userService.findById(userId));
        return "rates";
    }

    @PostMapping("/rates/{userId}")
    public String addRates(@PathVariable("userId") long userId, @Valid RatesTO rates, Model model) {
        model.addAttribute("roles", Role.values());
        model.addAttribute("rates", rates);
        model.addAttribute("languages", Language.values());
        model.addAttribute("user", userService.findById(userId));
        ratesService.registerRates(rates);
        return "main";
    }

    @GetMapping("/teacherRates/{userId}")
    public String registerRatesForTeacher(@PathVariable("userId") long userId, TeacherRatesTO rates, Model model) {
        model.addAttribute("roles", Role.values());
        model.addAttribute("rates", rates);
        model.addAttribute("languages", Language.values());
        model.addAttribute("user", userService.findById(userId));
        return "teacherRates";
    }

    @PostMapping("/teacherRates/{userId}")
    public String addRatesForTeacher(@PathVariable("userId") long userId, @Valid TeacherRatesTO rates, Model model) {
        model.addAttribute("roles", Role.values());
        model.addAttribute("rates", rates);
        model.addAttribute("languages", Language.values());
        model.addAttribute("user", userService.findById(userId));
        ratesService.registerTeacherRates(rates);
        return "main";
    }

    @GetMapping("/assign2Rates/{userId}")
    public String getRates(@PathVariable("userId") long userId, Model model) {
        model.addAttribute("user", userService.findById(userId));
        model.addAttribute("rates", ratesService.getAllRatesTO());
        model.addAttribute("users", userService.getAllStudents());
        return "assignStudents";
    }

    @PostMapping("/assign2Rates/{userId}")
    public String assignUserToRates(@PathVariable("userId") long userId, UpdateStudentTO studentToAssign, String rateDescription, Language language, Model model) {
        model.addAttribute("user", userService.findById(userId));
        model.addAttribute("rates", ratesService.getAllRatesTO());
        model.addAttribute("users", userService.getAllStudents());
        model.addAttribute("language", Language.values());
        model.addAttribute("rateDescription", ratesService.getAllDescriptions(Role.STUDENT));
        RatesTO rates = ratesService.getByLangAndDescription(language, rateDescription);
        manageUsersService.assignStudentsToRates(studentToAssign,rates);
        return "main";
    }

     @GetMapping("/assignTeacher2Rates/{userId}")
     public String getTeacherRates(@PathVariable("userId") long userId, Model model){
         model.addAttribute("user", userService.findById(userId));
         model.addAttribute("rates", ratesService.getRatesByRole(Role.TEACHER));
         model.addAttribute("users", userService.getAllTeachers());
        return "assignTeacher2Rates";
     }

     @PostMapping("/assignTeacher2Rates/{userId}")
     public String assignTeachers2Rates(@PathVariable("userId") long userId, UpdateTeacherTO teacherToAssign, Language language, Model model){
         model.addAttribute("user", userService.findById(userId));
         model.addAttribute("rates", ratesService.getRatesByRole(Role.TEACHER));
         model.addAttribute("users", userService.getAllTeachers());
         model.addAttribute("language", Language.values());
         RatesTO ratesTO = ratesService.getByRoleAndLang(Role.TEACHER, language);
         manageUsersService.assignTeacherToRates(teacherToAssign, ratesTO);
         return "main";
     }





}
