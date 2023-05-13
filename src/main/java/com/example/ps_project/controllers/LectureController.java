package com.example.ps_project.controllers;

import com.example.ps_project.DTOs.DTO;
import com.example.ps_project.services.LectureService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping (path = "/api/lecture")
@AllArgsConstructor
public class LectureController {
    private final LectureService lectureService;

   /* @PostMapping(path = "/upload")
    public ResponseEntity<DTO> upload(@ModelAttribute LectureDTO lectureDTO) {
        return lectureService.saveNewLecture(lectureDTO);
    }*/

    @DeleteMapping(path = "deleteLecture")
    public ResponseEntity<DTO> deleteLecture(@RequestBody int id) {return lectureService.deleteLecture(id);}
}
