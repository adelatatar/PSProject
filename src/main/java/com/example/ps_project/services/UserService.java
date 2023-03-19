package com.example.ps_project.services;

import com.example.ps_project.entities.User;
import com.example.ps_project.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }

    public ResponseEntity<User> registerNewUser(User newUser) {
        Optional<User> user = userRepository.findByEmail(newUser.getEmail());
        if(user.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(newUser);
        }

        userRepository.save(newUser);

        if(userRepository.findById(newUser.getId()).isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(newUser);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(newUser);
        }
    }

    public ResponseEntity<User> deleteUser(User userToDelete) {
        Optional<User> user = userRepository.findByEmail(userToDelete.getEmail());
        if(user.isPresent()) {
           userRepository.delete(userToDelete);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userToDelete);
        }

        if(userRepository.findById(userToDelete.getId()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(userToDelete);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(userToDelete);
        }
    }

    public ResponseEntity<User> updateUser(String password, User userToUpdate) {
        Optional<User> user = userRepository.findByEmail(userToUpdate.getEmail());
        if(user.isPresent()){
            if(password.length() >= 8) {
                userToUpdate.setPassword(password);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userToUpdate);
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userToUpdate);
        }

        if(userToUpdate.getPassword().equals(password)) {
            return ResponseEntity.status(HttpStatus.OK).body(userToUpdate);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(userToUpdate);
        }
    }
}
