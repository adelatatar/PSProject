package com.example.ps_project.controllers;

import com.example.ps_project.DTOs.UpdateUserDTO;
import com.example.ps_project.DTOs.UserDTO;
import com.example.ps_project.entities.User;
import com.example.ps_project.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * In clasa UserController se realizeaza maparea: cerere care vine de pe frontend - metoda implementata in UserService.
 */
@RestController
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping(path = "/user/getAll")
    public List<User> getAllUsers(){
        return userService.getUsers();
    }

    @PostMapping(path = "/user/register")
    public ResponseEntity<UserDTO> registerNewUser(@RequestBody UserDTO newUser) { return userService.registerNewUser(newUser);}

    @DeleteMapping(path = "/user/delete")
    public  ResponseEntity<User> deleteUser(@RequestBody int id) { return userService.deleteUser(id);}

    @PutMapping(path = "/user/update")
    public ResponseEntity<UpdateUserDTO> updateUser(@RequestBody UpdateUserDTO userToUpdate) {
        return userService.changePassword(userToUpdate);
    }
}
