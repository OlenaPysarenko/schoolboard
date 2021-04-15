package com.ua.schoolboard.service.services;

import com.ua.schoolboard.exceptions.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class CustomExceptionResolver {

    public String resolveError(Model model, String redirect, CustomException e) {
        model.addAttribute("error", e.getErrorCode().name());
        return redirect;
    }
}
