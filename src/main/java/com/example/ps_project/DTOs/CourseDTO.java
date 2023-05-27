package com.example.ps_project.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO implements DTO{
    private Integer id;
    private String name;
    private String category;
    private Integer price;
    private String description;
}
