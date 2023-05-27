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

    /**
     * public List<User> getUsers(): Returneaza o lista cu toti utilizatorii din baza de date.
     * @return
     */
    @GetMapping(path = "getAll")
    public List<User> getAllUsers(){
        return userService.getUsers();
    }

    /**
     * Inregistrarea unui nou user pe site. Se cauta user-ul dupa email (pentru a nu fi deja inregistrat),
     * daca nu este gasit si datele introduse respecta conditiile inregistraea se realizeaza cu succes,
     * aftel se va afisa un mesaj de eraoare.
     * @param newUser
     * @return
     */
    @PostMapping(path = "register")
    public ResponseEntity<DTO> registerNewUser(@RequestBody UserDTO newUser) { return userService.registerNewUser(newUser);}

    /**
     * Se realizeaza stergerea unui anumit user, identificat prin id-ul acestuia. Daca acesta este gasit in baza de date,
     * se sterge si se returneaza un mesaj de succes, altfet, daca nu este gasit, se returneaza un mesaj de  eroare.
     * @param id
     * @return
     */
    @DeleteMapping(path = "delete")
    public  ResponseEntity<DTO> deleteUser(@RequestBody int id) { return userService.deleteUser(id);}

    /**
     * Se cauta user-ul dupa email-ul acestuia, daca este gasit si noua parola respecta conditiile impuse,
     * parola va fi actualizata, altfel se va returna un mesaj de eroare.
     * @param userToUpdate
     * @return
     */
    @PutMapping(path = "update")
    public ResponseEntity<DTO> updateUser(@RequestBody UpdateUserDTO userToUpdate) {
        return userService.changePassword(userToUpdate);
    }

    /**
     * Autentificarea utilizatorului pe site are loc doar daca datele intreoduse sunt corecte,
     * altfel se afiseaza un mesaj de eroare.
     * @param loginUserDTO
     * @return
     */
    @PostMapping(path = "login")
    public ResponseEntity<DTO> loginUser(@RequestBody LoginUserDTO loginUserDTO) {
        return userService.loginUser(loginUserDTO);
    }
}
