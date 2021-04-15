package com.ua.schoolboard.rest.handler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

   /* @ExceptionHandler(RuntimeException.class)
    public String handleException(Exception e, Model model) {
        model.addAttribute("msg", e.getMessage());
        return "error";
    }*/
}
