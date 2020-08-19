package com.ua.schoolboard.rest.controllers;

import com.ua.schoolboard.persistence.mappers.ClassEntityMapper;
import com.ua.schoolboard.persistence.model.ClassSessionEntity;
import com.ua.schoolboard.rest.model.*;
import com.ua.schoolboard.service.mappers.ClassSessionMapper;
import com.ua.schoolboard.service.mappers.GroupMapper;
import com.ua.schoolboard.service.mappers.UserMapper;
import com.ua.schoolboard.service.model.ClassSessionBO;
import com.ua.schoolboard.service.services.ClassService;
import com.ua.schoolboard.service.services.GroupService;
import com.ua.schoolboard.service.services.PaymentService;
import com.ua.schoolboard.service.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ManageUsersController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final GroupService groupService;
    private final ClassSessionMapper classMapper;
    private final ClassService classService;
    private final PaymentService paymentService;
    private final StudentsController studentsController;

    @GetMapping("/assignUser2Group/{userId}")
    public String getUsersAndGroups(@PathVariable("userId") long userId, Model model) {
        model.addAttribute("user", userService.findById(userId));
        model.addAttribute("groups", groupService.listOfGroups());
        model.addAttribute("users", userService.getAllStudents());
        model.addAttribute("languages", Language.values());
        return "assignStudents2Group";
    }

    @PostMapping("/assignUser2Group/{userId}")
    public String assignUser2Group(@PathVariable("userId") long userId, UpdateStudentTO student, String groupName, Model model) {
        model.addAttribute("user", userService.findById(userId));
        model.addAttribute("group", groupService.listOfGroups());
        model.addAttribute("users", userService.getAllStudents());
        model.addAttribute("languages", Language.values());
        GroupTO group = groupService.getGroupByName(groupName);
        UpdateStudentTO studentTO = userService.getStudentByNameAndSurname(student.getName(), student.getSurname());
        groupService.assignUserToGroup(group, studentTO.getUserId());
        return "main";
    }


    @GetMapping("/assignTeacher2Group/{userId}/{groupId}")
    public String getTeachers4Group(@PathVariable("userId") long userId, @PathVariable("groupId") long groupId, Model model) {
        model.addAttribute("user", userService.findById(userId));
        model.addAttribute("group", groupService.getGroupById(groupId));
        model.addAttribute("teachers", userService.getAllTeachers());
        //model.addAttribute("teacher", teacher);
        return "assignTeacher2Group";
    }

    @PostMapping("/assignTeacher2Group/{userId}/{groupId}")
    public String assignTeacher2Group(@PathVariable("userId") long userId, @PathVariable("groupId") long groupId, Long teacherId, Model model) {
        UpdateTeacherTO teacher = userService.getTeacher(teacherId);
        groupService.assignUserToGroup(groupService.getGroupById(groupId), teacher.getUserId());
        model.addAttribute("user", userService.findById(userId));
        model.addAttribute("groups", groupService.getAll());
        return "allGroups";
    }

    @GetMapping("/room/{userId}")
    public String getTeacherRoom(@PathVariable("userId") long userId, Model model) {
        model.addAttribute("user", userService.findById(userId));
        model.addAttribute("groups", groupService.listByTeacher(userId));
        model.addAttribute("users", userService.getAllStudents());
        return "room";
    }

    @GetMapping("/classes/{userId}/{groupId}")
    public String getAllClasses(@PathVariable("userId") long userId, @PathVariable("groupId") long groupId, Model model) {
        model.addAttribute("user", userService.findById(userId));
        model.addAttribute("group", groupService.getGroupById(groupId));
        model.addAttribute("classes", classService.getClassesByTeacher(userId));
        model.addAttribute("userId", userId);
        return "classes";
    }

    /*   @PostMapping("/classes/{userId}")
       public String getAllStudents(@PathVariable("userId") long userId, Model model){
           model.addAttribute("user", userService.findById(userId));
           model.addAttribute("groups", groupService.listOfGroups());
           model.addAttribute("users", userService.getAllStudents());
           return "room";
       }*/
    @GetMapping("/addClass/{userId}/{groupId}")
    public String getNewClass(@PathVariable("userId") long userId, @PathVariable("groupId") long groupId, Model model) {
        model.addAttribute("user", userService.getTeacher(userId));
        model.addAttribute("userId", userId);
        model.addAttribute("group", groupService.getGroupById(groupId));
        model.addAttribute("groups", groupService.getAll());
        model.addAttribute("students", groupService.getGroupById(groupId).getStudents());
        ClassSessionTO classTO = new ClassSessionTO();
        model.addAttribute("classTO", classTO);
        List<UpdateStudentTO> emptyList = new ArrayList<>();
        classTO.setStudents(emptyList);

       List<UpdateStudentTO> students = groupService.getGroupById(groupId).getStudents();
        List<Long> studentIds = new ArrayList<>();
        for (UpdateStudentTO st : students) {
            studentIds.add(st.getUserId());
        }
        model.addAttribute("studentIds", studentIds);
        return "addClass";
    }

    @PostMapping("/addClass/{userId}/{groupId}")
    public String addNewClass(@PathVariable("userId") long userId, @PathVariable("groupId") long groupId, @ModelAttribute ClassSessionTO classTO, Model model, BindingResult result) {
        GroupTO groupById = groupService.getGroupById(groupId);
        classTO.setGroup(groupById);
        classTO.setTeacher(userService.getTeacher(userId));
        classService.register(classTO);
        model.addAttribute("group", groupService.getGroupById(groupId));
        model.addAttribute("userId", userId);
        model.addAttribute("user", userService.getTeacher(userId));
        model.addAttribute("groups", groupService.getAll());
        model.addAttribute("users", userService.getAllStudents());
        model.addAttribute("students", groupService.getGroupById(groupId).getStudents());
        return "room";
    }
    /*@ModelAttribute("students")
    public Collection<UpdateStudentTO> populateStudents(long groupId){
        return groupService.getGroupById(groupId).getStudents();
    }*/


    @GetMapping("/allTeachers/{userId}")
    public String getAllTeachers(@PathVariable("userId") long userId, Model model) {
        model.addAttribute("user", userService.findById(userId));
        model.addAttribute("teachers", userService.getAllByRole(Role.TEACHER));
        return "allTeachers";
    }


    @GetMapping("/salary/{userId}/{teacherId}")
    public String getSalary(@PathVariable("userId") long userId, @PathVariable("teacherId") long teacherId, Model model) {
        UpdateTeacherTO teacher = userService.getTeacher(teacherId);
        paymentService.updateBalances(userId, teacherId);
        model.addAttribute("admin", userService.findById(userId));
        model.addAttribute("teacher", teacher);
        //model.addAttribute("teachers",userService.getAllByRole(Role.TEACHER));
        return "salary";
    }


}
