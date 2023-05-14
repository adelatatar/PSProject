package com.example.ps_project;

import com.example.ps_project.DTOs.*;
import com.example.ps_project.entities.User;
import com.example.ps_project.repositories.UserRepository;
import com.example.ps_project.services.UserService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private List<User> userList;

    @BeforeEach
    public void setup(){
        userList = new ArrayList<>();
        userList.add(new User(1, "Adela", "Tatar", 22, "adela", "adela@yahoo.com", null, null));
        userList.add(new User(2, "Marta", "Tatar", 21, "marta", "marta@gmail.com", null, null));
        userList.add(new User(3, "Maria", "Tatar", 16, "maria", "maria@yahoo.com", null, null));
        userList.add(new User(4, "Marina", "Tatar", 11, "marina", "marina@gmail.com", null, null));
        userList.add(new User(5, "Adrian", "Tatar", 8, "adrian", "adrian@yahoo.com", null, null));
        userList.add(new User(6, "Elena", "Tatar", 6, "elena", "elena@gmail.com", null, null));
    }

    @Test
    public void getUsersTest() {
        when(userRepository.findAll()).thenReturn(userList);

        List<User> allUsers = userService.getUsers();

        assertEquals(userList, allUsers);
    }

    @Test
    public void registerUserTest_NewUser(){
        UserDTO userDTO = new UserDTO(78, "Adriana", "Tatar", 43, "adriana", "adriana@yahoo.com");
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setAge(userDTO.getAge());

        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        ResponseEntity<DTO> response = userService.registerNewUser(userDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        UserDTO savedUserDTO = (UserDTO) response.getBody();
        assertEquals(user.getId(), savedUserDTO.getId());
        assertEquals(user.getFirstName(), savedUserDTO.getFirstName());
        assertEquals(user.getLastName(), savedUserDTO.getLastName());
    }

    @Test
    public void registerUser_ExistingUser(){
        UserDTO userDTO = new UserDTO(78, "Adriana", "Tatar", 43, "adriana", "adriana@yahoo.com");
        User existingUser = new User();
        existingUser.setFirstName(userDTO.getFirstName());
        existingUser.setLastName(userDTO.getLastName());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setAge(userDTO.getAge());
        existingUser.setPassword(userDTO.getPassword());

        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.of(existingUser));

        ResponseEntity<DTO> response = userService.registerNewUser(userDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(userDTO, response.getBody());
    }

    @Test
    public void deleteUserTest_Success(){
        int userId = 78;
        Optional<User> mockUser = Optional.of(new User(78, "Adriana", "Tatar", 43, "adriana", "adriana@yahoo.com", null, null));

        when(userRepository.findById(userId)).thenReturn(mockUser);

        ResponseEntity<DTO> response = userService.deleteUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("The user was deleted!", ((SuccessMessage) response.getBody()).getSuccessMessage());
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    public void deleteUserTest_NotFound(){
        int userId = 78;
        Optional<User> mockUser = Optional.empty();

        when(userRepository.findById(userId)).thenReturn(mockUser);

        ResponseEntity<DTO> response = userService.deleteUser(userId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("There is no user with this id!", ((ErrorDTO) response.getBody()).getErrorMessage());
        verify(userRepository, never()).deleteById(userId);
    }

    @Test
    public void deleteUserTest_NotDeleted(){
        int userId = 78;
        User user = new User(78, "Adriana", "Tatar", 43, "adriana", "adriana@yahoo.com", null, null);
        Optional<User> mockUser = Optional.of(user);

        when(userRepository.findById(userId)).thenReturn(mockUser);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(mockUser);

        ResponseEntity<DTO> response = userService.deleteUser(userId);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("The user wasn't deleted!", ((ErrorDTO) response.getBody()).getErrorMessage());
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    public void loginUserTest_Success() {
        LoginUserDTO loginUserDTO = new LoginUserDTO("adela@yahoo.com", "adela");
        User user = new User();
        user.setEmail(loginUserDTO.getEmail());
        user.setPassword(loginUserDTO.getPassword());

        when(userRepository.findByEmail(loginUserDTO.getEmail())).thenReturn(Optional.of(user));

        ResponseEntity<DTO> response = userService.loginUser(loginUserDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Success!", ((SuccessMessage) response.getBody()).getSuccessMessage());
    }

    @Test
    public void loginUserTest_WrongEmail() {
        LoginUserDTO loginUserDTO = new LoginUserDTO("adela@yahoo.com", "adela");
        Optional<User> mockUser = Optional.empty();

        when(userRepository.findByEmail(loginUserDTO.getEmail())).thenReturn(mockUser);

        ResponseEntity<DTO> response = userService.loginUser(loginUserDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Email is wrong!", ((ErrorDTO) response.getBody()).getErrorMessage());
    }

    @Test
    public void loginUserTest_WrongPassword() {
        LoginUserDTO loginUserDTO = new LoginUserDTO("adela@yahoo.com", "adela");
        User user = new User();
        user.setEmail(loginUserDTO.getEmail());
        user.setPassword("blabla");
        Optional<User> mockUser = Optional.of(user);

        when(userRepository.findByEmail(loginUserDTO.getEmail())).thenReturn(mockUser);

        ResponseEntity<DTO> response = userService.loginUser(loginUserDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Password is wrong!", ((ErrorDTO) response.getBody()).getErrorMessage());
    }

    @Test
    public void changePasswordTest_Success() {
        UserDTO existingUser = new UserDTO();
        existingUser.setPassword("adelatatar");
        existingUser.setEmail("adela@gmail.com");
        UpdateUserDTO userToUpdate = new UpdateUserDTO("newPassword", existingUser);

        User user = new User();
        user.setEmail(existingUser.getEmail());
        user.setPassword(existingUser.getPassword());
        when(userRepository.findByEmail(userToUpdate.getUserDTO().getEmail())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        ResponseEntity<DTO> response = userService.changePassword(userToUpdate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("The password is changed!", ((SuccessMessage) response.getBody()).getSuccessMessage());
    }

    @Test
    public void changePasswordTest_WrongUser() {
        UserDTO existingUser = new UserDTO();
        existingUser.setPassword("useruseruser");
        existingUser.setEmail("user@gmail.com");
        UpdateUserDTO userToUpdate = new UpdateUserDTO("newPassword", existingUser);

        when(userRepository.findByEmail(userToUpdate.getUserDTO().getEmail())).thenReturn(Optional.empty());

        ResponseEntity<DTO> response = userService.changePassword(userToUpdate);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("The user doesn't exist!", ((ErrorDTO) response.getBody()).getErrorMessage());;
    }

    @Test
    public void changePasswordTest_BadPassword() {
        UserDTO existingUser = new UserDTO();
        existingUser.setPassword("adelatatar");
        existingUser.setEmail("adela@gmail.com");
        UpdateUserDTO userToUpdate = new UpdateUserDTO("adela", existingUser);

        User user = new User();
        user.setEmail(existingUser.getEmail());
        user.setPassword(existingUser.getPassword());
        when(userRepository.findByEmail(userToUpdate.getUserDTO().getEmail())).thenReturn(Optional.of(user));

        ResponseEntity<DTO> response = userService.changePassword(userToUpdate);


        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("The password is too short!", ((ErrorDTO) response.getBody()).getErrorMessage());;
    }
}