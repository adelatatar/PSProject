package com.example.ps_project.services;

import com.example.ps_project.DTOs.*;
import com.example.ps_project.entities.User;
import com.example.ps_project.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * In clasa UserService sunt implementate metodele ce se realizeaza pe clasa User.
 */
@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    /**
     * metoda getUser apeleaza metoda findAll() specifica interfetei UserRepository.
     *
     * @return lista cu toti useri inregistrati in tabela
     */
    public List<User> getUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }

    /**
     * metoda registerNewUser -- inregistreaza un nou user in tabela corespunzatoare din baza de date
     *
     * @param userDTO
     * @return un mesaj care spune statusul cererii efectuate -- BAD_REQUEST, BAD_GATEWAY, OK.
     */
    public ResponseEntity<DTO> registerNewUser(UserDTO userDTO) {
        Optional<User> newUser = userRepository.findByEmail(userDTO.getEmail());
        if (newUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userDTO);
        }

        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setAge(userDTO.getAge());

        User savedUser = userRepository.save(user);

        UserDTO savedUserDTO = new UserDTO();
        savedUserDTO.setId(savedUser.getId());
        savedUserDTO.setFirstName(savedUser.getFirstName());
        savedUserDTO.setLastName(savedUser.getLastName());
        if (userRepository.findById(user.getId()).isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(savedUserDTO);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(savedUserDTO);
        }
    }

    /**
     * Metoda deleteUser care sterge un anumit user.
     *
     * @param id
     * @return un mesaj care spune statusul cererii efectuate -- BAD_REQUEST, BAD_GATEWAY, OK.
     */
    public ResponseEntity<User> deleteUser(int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(user.get());
        }

        if (userRepository.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(user.get());
        }
    }

    /**
     * Metoda changePassword modifica parola unui anumit user.
     *
     * @param userToUpdate
     * @return un mesaj care spune statusul cererii efectuate -- BAD_REQUEST, BAD_GATEWAY, OK.
     */
    public ResponseEntity<DTO> changePassword(UpdateUserDTO userToUpdate) {
        Optional<User> user = userRepository.findByEmail(userToUpdate.getUserDTO().getEmail());
        if (user.isPresent()) {
            if (userToUpdate.getPassword().length() >= 8) {
                User userFound = user.get();
                userFound.setPassword(userToUpdate.getPassword());
                User savedUser = userRepository.save(userFound);

                if (userToUpdate.getPassword().equals(savedUser.getPassword())) {
                    return ResponseEntity.status(HttpStatus.OK).body(userToUpdate);
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(userToUpdate);
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userToUpdate);
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userToUpdate);
        }
    }

    public ResponseEntity<DTO> loginUser(LoginUserDTO loginUserDTO) {
        Optional<User> user = userRepository.findByEmail(loginUserDTO.getEmail());
        if(user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO("Email is wrong!"));
        }
        User foundUser = user.get();
        if(foundUser.getPassword().equals(loginUserDTO.getPassword())) {
            return ResponseEntity.status(HttpStatus.OK).body(loginUserDTO);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO("Password is wrong!"));
        }
    }
}
