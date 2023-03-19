package com.example.ps_project.controllers;

import com.example.ps_project.entities.User;
import com.example.ps_project.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping(path = "/user/getAll")
    public List<User> getAllUsers(){
        return userService.getUsers();
    }
}
