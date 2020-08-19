package com.ua.schoolboard.rest.controllers;

import com.ua.schoolboard.rest.model.*;
import com.ua.schoolboard.service.services.GroupService;
import com.ua.schoolboard.service.services.UserService;
import lombok.RequiredArgsConstructor;
import org.dom4j.rule.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;
    private final UserService userService;

    @RolesAllowed({"ADMIN"})
    @GetMapping("/group/{userId}")
    public String registerGroup(@PathVariable("userId") long userId, GroupTO group, Model model) {
        model.addAttribute("user", userService.findById(userId));
        model.addAttribute("group", group);
        model.addAttribute("groupTypes", GroupType.values());
        model.addAttribute("languages", Language.values());
        model.addAttribute("levels", Levels.values());
        return "group";
    }

    @PostMapping("/group/{userId}")
    public String addGroup(@PathVariable("userId") long userId, GroupTO group, Model model) {
        model.addAttribute("user", userService.findById(userId));
        model.addAttribute("group", group);
        model.addAttribute("groupTypes", GroupType.values());
        model.addAttribute("languages", Language.values());
        model.addAttribute("levels", Levels.values());
        groupService.createNewGroup(group);
        return "main";
    }

    @GetMapping("/allGroups/{userId}")
    public String getGroups(@PathVariable("userId") long userId, Model model) {
        model.addAttribute("user", userService.findById(userId));
        model.addAttribute("groups", groupService.getAll());
        return "allGroups";
    }

    @GetMapping("/groupEdit/{userId}/{groupId}")
    public String editGroup(@PathVariable("userId") Long userId, @PathVariable("groupId") Long groupId, Model model){
        model.addAttribute("userId", userId);
        model.addAttribute("groups", groupService.listOfGroups());
        model.addAttribute("group", groupService.getGroupById(groupId));
        model.addAttribute("users", userService.getAllStudents());
        model.addAttribute("groupTypes",GroupType.values());
        model.addAttribute("languages", Language.values());
        model.addAttribute("groupId", groupId);
        model.addAttribute("levels", Levels.values());
        return "groupEdit";
    }

    @PostMapping("/groupEdit/{userId}/{groupId}")
    public String editGroup1(@PathVariable("userId") Long userId, @PathVariable("groupId")Long groupId, UpdGroupTO groupTO, Model model){
        GroupTO group = groupService.updateGroup(groupTO);
        model.addAttribute("user", userService.findById(userId));
        model.addAttribute("groups", groupService.listOfGroups());
        model.addAttribute("users", userService.getAllStudents());
        model.addAttribute("groupTypes",GroupType.values());
        model.addAttribute("languages", Language.values());
        model.addAttribute("groupId", groupId);
        model.addAttribute("levels", Levels.values());
        model.addAttribute("group", group);
        return "allGroups";
    }


     @GetMapping("/disableGroup/{userId}/{groupId}")
    public String disableGroup(@PathVariable("userId") Long userId, @PathVariable("groupId")Long groupId, Model model){
         model.addAttribute("user", userService.findById(userId));
        // model.addAttribute("groups", groupService.listOfGroups());
        model.addAttribute("group", groupService.getGroupById(groupId));
         model.addAttribute("groupId", groupId);
        return "allGroups";
     }

}
