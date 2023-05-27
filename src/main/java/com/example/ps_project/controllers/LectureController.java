package com.example.ps_project.controllers;

import com.example.ps_project.DTOs.DTO;
import com.example.ps_project.DTOs.LectureDTO;
import com.example.ps_project.services.CourseService;
import com.example.ps_project.services.LectureService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping (path = "/api/lecture")
@AllArgsConstructor
public class LectureController {
    private final LectureService lectureService;

    /**
     * Se realizeaza stergerea unei anumite lectii in functie de id-ul acesteia. Prima data se cauta lectia,
     * daca nu se gaseste se returneaza un mesaj de eroare, iar in cazul in care se gaseste se sterge atat
     * din tabela Lecture, cat si toate aparitiile din tabela LectureInCourses.
     * @param id
     * @return
     */
    @DeleteMapping(path = "deleteLecture")
    public ResponseEntity<DTO> deleteLecture(@RequestBody int id) {return lectureService.deleteLecture(id);}
}
