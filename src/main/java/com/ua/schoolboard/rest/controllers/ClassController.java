package com.ua.schoolboard.rest.controllers;

import com.ua.schoolboard.service.mappers.ClassSessionMapper;
import com.ua.schoolboard.service.services.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ClassController {
    private final ClassService classService;
    private final ClassSessionMapper classMapper;


}
