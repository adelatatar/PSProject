package com.example.ps_project.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LectureDTO implements DTO{
    private Integer id;
    private String name;
    private MultipartFile file;
}
