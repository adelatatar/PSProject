package com.example.ps_project.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrolStudentDTO implements DTO {
    private Integer userID;
    private Integer courseID;
}
