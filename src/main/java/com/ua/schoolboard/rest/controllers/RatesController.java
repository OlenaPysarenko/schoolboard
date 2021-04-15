package com.ua.schoolboard.rest.controllers;

import com.ua.schoolboard.exceptions.CustomException;
import com.ua.schoolboard.rest.model.*;
import com.ua.schoolboard.service.services.CustomExceptionResolver;
import com.ua.schoolboard.service.services.ManageUsersService;
import com.ua.schoolboard.service.services.RatesService;
import com.ua.schoolboard.service.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;


@Controller
@RequiredArgsConstructor
public class RatesController {
    private final RatesService ratesService;
    private final UserService userService;
    private final ManageUsersService manageUsersService;
    private final CustomExceptionResolver resolver;

    //@RolesAllowed({"ADMIN"})
    @GetMapping("/rates/{userId}")
    public String registerRates(@PathVariable("userId") long userId, RatesTO rates, Model model) {
        model.addAttribute("roles", Role.values());
        model.addAttribute("rates", rates);
        model.addAttribute("languages", Language.values());
        try {
            model.addAttribute("user", userService.findById(userId));
        } catch (CustomException e) {
            resolver.resolveError(model, "main", e);
        }
        return "rates";
    }

    @PostMapping("/rates/{userId}")
    public String addRates(@PathVariable("userId") long userId, @Valid RatesTO rates, Model model) {
        model.addAttribute("roles", Role.values());
        model.addAttribute("rates", rates);
        model.addAttribute("languages", Language.values());
        try {
            model.addAttribute("user", userService.findById(userId));
        } catch (CustomException e) {
            resolver.resolveError(model, "main", e);
        }
        ratesService.registerRates(rates);
        return "main";
    }

    @GetMapping("/teacherRates/{userId}")
    public String registerRatesForTeacher(@PathVariable("userId") long userId, TeacherRatesTO rates, Model model) {
        model.addAttribute("roles", Role.values());
        model.addAttribute("rates", rates);
        model.addAttribute("languages", Language.values());
        try {
            model.addAttribute("user", userService.findById(userId));
        } catch (CustomException e) {
            resolver.resolveError(model, "main", e);
        }
        return "teacherRates";
    }

    @PostMapping("/teacherRates/{userId}")
    public String addRatesForTeacher(@PathVariable("userId") long userId, @Valid TeacherRatesTO rates, Model model) {
        model.addAttribute("roles", Role.values());
        model.addAttribute("rates", rates);
        model.addAttribute("languages", Language.values());
        try {
            model.addAttribute("user", userService.findById(userId));
        } catch (CustomException e) {
            resolver.resolveError(model, "main", e);
        }
        ratesService.registerTeacherRates(rates);
        return "main";
    }

    @GetMapping("/assign2Rates/{userId}")
    public String getRates(@PathVariable("userId") long userId, Model model) {
        try {
            model.addAttribute("user", userService.findById(userId));
        } catch (CustomException e) {
            resolver.resolveError(model, "main", e);
        }
        model.addAttribute("rates", ratesService.getAllRatesTO());
        model.addAttribute("users", userService.getAllStudents());
        return "assignStudents";
    }

    @PostMapping("/assign2Rates/{userId}")
    public String assignUserToRates(@PathVariable("userId") long userId, UpdateStudentTO studentToAssign, String rateDescription, Language language, Model model) throws CustomException {
        try {
            model.addAttribute("user", userService.findById(userId));
        } catch (CustomException e) {
            resolver.resolveError(model, "main", e);
        }
        model.addAttribute("rates", ratesService.getAllRatesTO());
        model.addAttribute("users", userService.getAllStudents());
        model.addAttribute("language", Language.values());
        model.addAttribute("rateDescription", ratesService.getAllDescriptions(Role.STUDENT));
        RatesTO rates = ratesService.getByLangAndDescription(language, rateDescription);
        manageUsersService.assignStudentsToRates(studentToAssign, rates);
        return "main";
    }

    @GetMapping("/assignTeacher2Rates/{userId}")
    public String getTeacherRates(@PathVariable("userId") long userId, Model model) {
        try {
            model.addAttribute("user", userService.getTeacher(userId));

        } catch (CustomException e) {
            resolver.resolveError(model, "main", e);
        }
        model.addAttribute("languages", Language.values());
        model.addAttribute("rates", ratesService.getRatesByRole(Role.TEACHER));
        model.addAttribute("teachers", userService.getAllTeachers());
        return "assignTeacher2Rates";
    }

    @PostMapping("/assignTeacher2Rates/{userId}")
    public String assignTeachers2Rates(@PathVariable("userId") long userId, Long teacherId, Language language, Model model) {
        RatesTO ratesTO = null;
        UpdateTeacherTO teacherToAssign = null;
        try {
            teacherToAssign = userService.getTeacher(teacherId);
            model.addAttribute("teacher", teacherToAssign);
            model.addAttribute("user", userService.findById(userId));
            ratesTO = ratesService.getByRoleAndLang(Role.TEACHER, language);
            manageUsersService.assignTeacherToRates(teacherToAssign, ratesTO);
        } catch (CustomException e) {
            resolver.resolveError(model, "main", e);
        }
        model.addAttribute("rates", ratesService.getRatesByRole(Role.TEACHER));
        model.addAttribute("users", userService.getAllTeachers());
        model.addAttribute("language", Language.values());
        return "main";
    }


}
