package com.example.ps_project.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements DTO{
    private Integer id;
    private String firstName;
    private String lastName;
    private int age;
    private String password;
    private String email;
}
