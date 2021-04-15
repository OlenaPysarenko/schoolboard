package com.ua.schoolboard.rest.controllers;

import com.ua.schoolboard.exceptions.CustomException;
import com.ua.schoolboard.rest.model.*;
import com.ua.schoolboard.service.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class ManageUsersController {
    private final UserService userService;
    private final GroupService groupService;
    private final ClassService classService;
    private final PaymentService paymentService;
    private final CustomExceptionResolver resolver;

    @GetMapping("/assignUser2Group/{userId}")
    public String getUsersAndGroups(@PathVariable("userId") long userId, Model model) {
        try {
            model.addAttribute("user", userService.findById(userId));
        } catch (CustomException e) {
            resolver.resolveError(model, "main", e);
        }
        model.addAttribute("groups", groupService.listOfGroups());
        model.addAttribute("students", userService.getAllStudents());
        model.addAttribute("languages", Language.values());
        return "assignStudents2Group";
    }

    @PostMapping("/assignUser2Group/{userId}")
    public String assignUser2Group(@PathVariable("userId") long userId, Long studentId /*UpdateStudentTO student*/, String groupName, Model model) {
        UpdateStudentTO student = null;
        try {
            model.addAttribute("user", userService.findById(userId));
            GroupTO group = groupService.getGroupByName(groupName);
            groupService.assignUserToGroup(group, studentId);
        } catch (CustomException e) {
            e.getStackTrace();
             resolver.resolveError(model, "main", e);
        }
        model.addAttribute("group", groupService.listOfGroups());
        model.addAttribute("users", userService.getAllStudents());
        model.addAttribute("languages", Language.values());
        return "main";
    }

    @GetMapping("/assignTeacher2Group/{userId}/{groupId}")
    public String getTeachers4Group(@PathVariable("userId") long userId, @PathVariable("groupId") long groupId, Model model) {
        try {
            model.addAttribute("user", userService.findById(userId));
            model.addAttribute("group", groupService.getGroupById(groupId));
        } catch (CustomException e) {
            resolver.resolveError(model, "main", e);
        }
        model.addAttribute("teachers", userService.getAllTeachers());
        return "assignTeacher2Group";
    }

    @PostMapping("/assignTeacher2Group/{userId}/{groupId}")
    public String assignTeacher2Group(@PathVariable("userId") long userId, @PathVariable("groupId") long groupId, Long teacherId, Model model) {
        UpdateTeacherTO teacher = null;
        try {
            teacher = userService.getTeacher(teacherId);
            groupService.assignUserToGroup(groupService.getGroupById(groupId), teacher.getUserId());
            model.addAttribute("user", userService.findById(userId));
        } catch (CustomException e) {
            resolver.resolveError(model, "main", e);
        }
        model.addAttribute("groups", groupService.getAll());
        return "allGroups";
    }

    @GetMapping("/room/{userId}")
    public String getTeacherRoom(@PathVariable("userId") long userId, Model model) {
        try {
            model.addAttribute("user", userService.findById(userId));
            model.addAttribute("groups", groupService.listByTeacher(userId));
        } catch (CustomException e) {
            resolver.resolveError(model, "main", e);
             model.addAttribute("error",e.getMessage());
        }
        model.addAttribute("users", userService.getAllStudents());
        return "room";
    }

    @GetMapping("/classes/{userId}/{groupId}")
    public String getAllClasses(@PathVariable("userId") long userId, @PathVariable("groupId") Long groupId, Model model) {
        try {
            model.addAttribute("user", userService.findById(userId));
            model.addAttribute("group", groupService.getGroupById(groupId));
            model.addAttribute("classes", classService.getClassesByTeacher(userId));
            List<ClassSessionTO> classesByTeacher = classService.getClassesByTeacher(userId);
            for (ClassSessionTO c : classesByTeacher) {
                model.addAttribute("date", dateFormatter(c));
            }
        } catch (CustomException e) {
            resolver.resolveError(model, "room", e);
        }
        model.addAttribute("userId", userId);
        return "classes";
    }

    @PostMapping("/classes/{userId}")
    public String getAllStudents(@PathVariable("userId") long userId, Model model) {
        try {
            model.addAttribute("user", userService.findById(userId));
        } catch (CustomException e) {
            resolver.resolveError(model, "room", e);
        }
        model.addAttribute("groups", groupService.listOfGroups());
        model.addAttribute("users", userService.getAllStudents());
        return "room";
    }

    @GetMapping("/addClass/{userId}/{groupId}")
    public String getNewClass(@PathVariable("userId") long userId, @PathVariable("groupId") Long groupId, Model model) {
        try {
            model.addAttribute("user", userService.getTeacher(userId));
            model.addAttribute("group", groupService.getGroupById(groupId));
            model.addAttribute("students", groupService.getGroupById(groupId).getStudents());
            List<UpdateStudentTO> students = groupService.getGroupById(groupId).getStudents();
        } catch (CustomException e) {
            resolver.resolveError(model, "main", e);
        }
        model.addAttribute("userId", userId);
        model.addAttribute("groups", groupService.getAll());
        ClassSessionTO classTO = new ClassSessionTO();
        model.addAttribute("classTO", classTO);
        return "addClass";
    }

    @PostMapping("/addClass/{userId}/{groupId}")
    public String addNewClass(@PathVariable("userId") long userId, @PathVariable("groupId") Long groupId, @ModelAttribute ClassSessionTO classTO, Model model, BindingResult result) {
        StudentsSelected studentsPresent = new StudentsSelected();
        try {
            model.addAttribute("userId", userId);
            model.addAttribute("StudentsPresent", studentsPresent);
            GroupTO groupById = groupService.getGroupById(groupId);
            model.addAttribute("user", userService.getTeacher(userId));
            model.addAttribute("group", groupService.getGroupById(groupId));
            List<UpdateStudentTO> students = groupService.getGroupById(groupId).getStudents();
            //not sure how it works & supposed to
            for (UpdateStudentTO s : students) {
                checkStudent(userId, groupId, s.getUserId(), classTO.getClassId(), studentsPresent, model);
            }
            model.addAttribute("students", groupService.getGroupById(groupId).getStudents());
            setClass(userId, classTO, classTO.getHometask(), classTO.getClassDate(), studentsPresent, groupById);
            classService.register(classTO);
        } catch (CustomException e) {
            resolver.resolveError(model, "main", e);
        }
        model.addAttribute("groups", groupService.getAll());
        model.addAttribute("users", userService.getAllStudents());
        model.addAttribute("classTO", classTO);
        return "room";
    }

    private void setClass(@PathVariable("userId") long userId, @ModelAttribute ClassSessionTO classTO, String hometask, Date classDate, StudentsSelected studentsPresent, GroupTO groupById) throws CustomException {
        int classesCovered = groupById.getClassesCovered().size();
        classesCovered++;
        classTO.setClassesCovered(classesCovered);
        classTO.setGroup(groupById);
        classTO.setStudents(studentsPresent.getStudents());
        classTO.setTeacher(userService.getTeacher(userId));
        classTO.setHometask(hometask);
        classTO.setClassDate(classDate);

    }

    private String dateFormatter(ClassSessionTO classTO) {
        Date classDate = classTO.getClassDate();
        DateFormat outputFormatter = new SimpleDateFormat("dd/MM/yyyy");
        String date = outputFormatter.format(classDate);
        return date;
    }

    @GetMapping("checkStudent/{userId}/{groupId}/{studentId}")
    public String checkStudent(@PathVariable("userId") long userId, @PathVariable("groupId") Long groupId, @PathVariable("studentId") Long studentId, Long classId, StudentsSelected studentsPresent, Model model) {
        UpdateStudentTO student = null;
        try {
            model.addAttribute("user", userService.getTeacher(userId));
            model.addAttribute("group", groupService.getGroupById(groupId));
            model.addAttribute("students", groupService.getGroupById(groupId).getStudents());
            student = userService.getStudent(studentId);
            model.addAttribute("user", userService.getTeacher(userId));
        } catch (CustomException e) {
            resolver.resolveError(model, "addClass", e);
        }
        studentsPresent.addStudents(student);
        return "addClass";
    }

    @GetMapping("/editStudents/{userId}/{groupId}/{classId}/{studentId}")
    public String correctStudents(@PathVariable("userId") long userId, @PathVariable("groupId") Long groupId, @PathVariable("studentId") Long studentId, @PathVariable("classId") Long classId, StudentsSelected studentsPresent, Model model) {
        UpdateStudentTO student = null;
        try {
            model.addAttribute("user", userService.getTeacher(userId));
            model.addAttribute("group", groupService.getGroupById(groupId));
            model.addAttribute("students", groupService.getGroupById(groupId).getStudents());
            model.addAttribute("class", classService.getById(classId));
            student = userService.getStudent(studentId);
            model.addAttribute("user", userService.getTeacher(userId));
        } catch (CustomException e) {
            resolver.resolveError(model, "addClass", e);
        }
        studentsPresent.addStudents(student);
        return "editClass";
    }

    @GetMapping("/editClass/{userId}/{groupId}/{classId}")
    public String getClass(@PathVariable("userId") Long userId, @PathVariable("groupId") Long groupId, @PathVariable("classId") Long classId, Model model) {
        try {
            GroupTO groupById = groupService.getGroupById(groupId);
            ClassSessionTO classTO = classService.getById(classId);
            model.addAttribute("class", classTO);
            model.addAttribute("user", userService.getTeacher(userId));
            model.addAttribute("group", groupById);
            model.addAttribute("studentsPresent", classTO.getStudents());
            model.addAttribute("students", groupService.getGroupById(groupId).getStudents());
        } catch (CustomException e) {
            resolver.resolveError(model, "room", e);
        }
        model.addAttribute("groupId", groupId);
        model.addAttribute("groups", groupService.listOfActive());
        return "editClass";
    }

    @PostMapping("/editClass/{userId}/{groupId}/{classId}")
    public String editClass(@PathVariable("userId") Long userId, @PathVariable("classId") Long classId, @PathVariable("groupId") Long groupId, @ModelAttribute ClassSessionTO classTO, Model model) {
        StudentsSelected studentsPresent = new StudentsSelected();
        try {
            ClassSessionTO classById = classService.getById(classId);
            model.addAttribute("user", userService.getTeacher(userId));
            model.addAttribute("userId", userId);
            model.addAttribute("group", groupService.getGroupById(groupId));
            model.addAttribute("studentsPresent", classTO.getStudents());
            model.addAttribute("classTO", classTO);
            GroupTO groupById = groupService.getGroupById(groupId);
            List<UpdateStudentTO> students = groupService.getGroupById(groupId).getStudents();
            List<ClassSessionTO> classesByTeacher = classService.getClassesByTeacher(userId);
            model.addAttribute("classes", classesByTeacher);
            for (ClassSessionTO c : classesByTeacher) {
                model.addAttribute("date", dateFormatter(c));
            }
            for (UpdateStudentTO s : students) {
                correctStudents(userId, groupId, s.getUserId(), classId, studentsPresent, model);
            }
            model.addAttribute("students", students);
            setClass(userId, classTO, classTO.getHometask(), classById.getClassDate(), studentsPresent, groupById);
            classService.edit(classTO);
        } catch (CustomException e) {
            resolver.resolveError(model, "classes", e);
        }
        return "classes";
    }


    @GetMapping("/allTeachers/{userId}")
    public String getAllTeachers(@PathVariable("userId") long userId, Model model) {
        try {
            model.addAttribute("user", userService.findById(userId));
        } catch (CustomException e) {
            resolver.resolveError(model, "main", e);
        }
        model.addAttribute("teachers", userService.getAllByRole(Role.TEACHER));
        return "allTeachers";
    }

    @GetMapping("/salary/{userId}/{teacherId}")
    public String getSalary(@PathVariable("userId") long userId, @PathVariable("teacherId") long teacherId, Model model) {
        UpdateTeacherTO teacher = null;
        try {
            teacher = userService.getTeacher(teacherId);
            paymentService.updateBalances(userId, teacherId);
            model.addAttribute("admin", userService.findById(userId));
            model.addAttribute("salary", teacher.getBalance().getAmount());
        } catch (CustomException e) {
            resolver.resolveError(model, "adminRoom", e);
            e.printStackTrace();
        }
        model.addAttribute("teacher", teacher);
        return "salary";
    }

    @PostMapping("/salary/{userId}/{teacherId}")
    public String displaySalary(@PathVariable("userId") long userId, @PathVariable("teacherId") long teacherId, Model model) {
        UpdateTeacherTO teacher = null;
        try {
            teacher = userService.getTeacher(teacherId);
            model.addAttribute("admin", userService.findById(userId));
        } catch (CustomException e) {
            resolver.resolveError(model, "adminRoom", e);
            e.printStackTrace();
        }
        model.addAttribute("teacher", teacher);
        model.addAttribute("salary", teacher.getBalance().getAmount());
        return "salary";
    }


}
