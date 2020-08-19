package com.ua.schoolboard.rest.controllers;

import com.ua.schoolboard.rest.model.UpdateStudentTO;
import com.ua.schoolboard.service.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;

@Controller
@RequiredArgsConstructor
public class StudentsController {
    private final UserService userService;

    @InitBinder(value = "students")
    protected void initBinder(final WebDataBinder binder) {
        binder.registerCustomEditor(UpdateStudentTO.class, new PartPropertyEditor(userService));
    }


    private static class PartPropertyEditor extends PropertyEditorSupport {
        private UserService userService;

        private PartPropertyEditor(UserService userService) {
            this.userService = userService;
        }

        @Override
        public void setAsText(String studentId) {
            final UpdateStudentTO student = userService.getStudent(Long.parseLong(studentId));
            setValue(student);
        }

        @Override
        public String getAsText() {
            return ((UpdateStudentTO) getValue()).getUserId().toString();
        }
    }
}

