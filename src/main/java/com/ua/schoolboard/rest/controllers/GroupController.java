package com.ua.schoolboard.rest.controllers;

import com.ua.schoolboard.exceptions.CustomException;
import com.ua.schoolboard.rest.model.*;
import com.ua.schoolboard.service.services.CustomExceptionResolver;
import com.ua.schoolboard.service.services.GroupService;
import com.ua.schoolboard.service.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;
    private final UserService userService;
    private final CustomExceptionResolver resolver;


    @GetMapping("/group/{userId}")
    public String registerGroup(@PathVariable("userId") long userId, GroupTO group, Model model) {
        try {
            model.addAttribute("user", userService.findById(userId));
        } catch (CustomException e) {
            resolver.resolveError(model, "main", e);
        }
        model.addAttribute("group", group);
        model.addAttribute("groupTypes", GroupType.values());
        model.addAttribute("languages", Language.values());
        model.addAttribute("levels", Levels.values());
        return "group";
    }

    @PostMapping("/group/{userId}")
    public String addGroup(@PathVariable("userId") long userId, GroupTO group, Model model) {
        try {
            model.addAttribute("user", userService.findById(userId));
            groupService.createNewGroup(group);
        } catch (CustomException e) {
            resolver.resolveError(model, "room", e);
        }
        model.addAttribute("group", group);
        model.addAttribute("groupTypes", GroupType.values());
        model.addAttribute("languages", Language.values());
        model.addAttribute("levels", Levels.values());
        return "main";
    }

    @GetMapping("/allGroups/{userId}")
    public String getGroups(@PathVariable("userId") long userId, Model model) {
        try {
            model.addAttribute("user", userService.findById(userId));
        } catch (CustomException e) {
            resolver.resolveError(model, "room", e);
        }
        model.addAttribute("groups", groupService.getAll());
        return "allGroups";
    }

    @GetMapping("/groupEdit/{userId}/{groupId}")
    public String editGroup(@PathVariable("userId") Long userId, @PathVariable("groupId") Long groupId, Model model) {
        model.addAttribute("userId", userId);
        model.addAttribute("groups", groupService.listOfActive());
        try {
            model.addAttribute("group", groupService.getGroupById(groupId));
        } catch (CustomException e) {
            resolver.resolveError(model, "room", e);
        }
        model.addAttribute("users", userService.getAllStudents());
        model.addAttribute("groupTypes", GroupType.values());
        model.addAttribute("languages", Language.values());
        model.addAttribute("groupId", groupId);
        model.addAttribute("levels", Levels.values());
        return "groupEdit";
    }

    @PostMapping("/groupEdit/{userId}/{groupId}")
    public String editGroup1(@PathVariable("userId") Long userId, @PathVariable("groupId") Long groupId, UpdGroupTO groupTO, Model model) {
        GroupTO group = null;
        try {
            group = groupService.updateGroup(groupTO);
            model.addAttribute("user", userService.findById(userId));
        } catch (CustomException e) {
            resolver.resolveError(model, "room", e);
        }
        model.addAttribute("groups", groupService.listOfActive());
        model.addAttribute("users", userService.getAllStudents());
        model.addAttribute("groupTypes", GroupType.values());
        model.addAttribute("languages", Language.values());
        model.addAttribute("groupId", groupId);
        model.addAttribute("levels", Levels.values());
        model.addAttribute("group", group);
        return "allGroups";
    }


    @GetMapping("/disableGroup/{userId}/{groupId}")
    public String disableGroup(@PathVariable("userId") Long userId, @PathVariable("groupId") Long groupId, Model model) {
        try {
            groupService.disableGroup(groupService.getGroupById(groupId));
            model.addAttribute("user", userService.findById(userId));
            model.addAttribute("group", groupService.getGroupById(groupId));
        } catch (CustomException e) {
            resolver.resolveError(model, "room", e);
        }
        model.addAttribute("groupId", groupId);
        model.addAttribute("groups", groupService.listOfActive());
        return "allGroups";
    }

}
