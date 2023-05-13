package com.example.ps_project.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewCoursesDTO implements DTO{
    private List<CourseDTO> courses;
}
