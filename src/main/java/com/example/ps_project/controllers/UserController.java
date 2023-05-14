package com.example.ps_project.controllers;

import com.example.ps_project.DTOs.DTO;
import com.example.ps_project.DTOs.LoginUserDTO;
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
@RequestMapping(path = "/api/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping(path = "getAll")
    public List<User> getAllUsers(){
        return userService.getUsers();
    }

    @PostMapping(path = "register")
    public ResponseEntity<DTO> registerNewUser(@RequestBody UserDTO newUser) { return userService.registerNewUser(newUser);}

    @DeleteMapping(path = "delete")
    public  ResponseEntity<DTO> deleteUser(@RequestBody int id) { return userService.deleteUser(id);}

    @PutMapping(path = "update")
    public ResponseEntity<DTO> updateUser(@RequestBody UpdateUserDTO userToUpdate) {
        return userService.changePassword(userToUpdate);
    }

    @PostMapping(path = "login")
    public ResponseEntity<DTO> loginUser(@RequestBody LoginUserDTO loginUserDTO) {
        return userService.loginUser(loginUserDTO);
    }
}
