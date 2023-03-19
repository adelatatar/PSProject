package com.example.ps_project.controllers;

import com.example.ps_project.entities.User;
import com.example.ps_project.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping(path = "/user/getAll")
    public List<User> getAllUsers(){
        return userService.getUsers();
    }

    @PostMapping(path = "/user/register")
    public ResponseEntity<User> registerNewUser(@RequestBody User newUser) { return userService.registerNewUser(newUser);}

    @DeleteMapping(path = "/user/delete")
    public  ResponseEntity<User> deleteUser(@RequestBody User userToDelete) { return userService.deleteUser(userToDelete);}

    @PutMapping(path = "/user/update")
    public ResponseEntity<User> updateUser(@RequestBody String password, User userToUpdate) {return userService.updateUser(password, userToUpdate);}
}
