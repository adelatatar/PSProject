package com.example.ps_project.controllers;

import com.example.ps_project.DTOs.DTO;
import com.example.ps_project.DTOs.EnrolStudentDTO;
import com.example.ps_project.DTOs.UsersCoursesDTO;
import com.example.ps_project.services.EnrolmentsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/enrolment")
@AllArgsConstructor
public class EnrolmentController {
    private final EnrolmentsService enrolmentsService;

    @GetMapping(path = "getCoursesByUser")
    public ResponseEntity<DTO> getCoursesByUser(@RequestBody UsersCoursesDTO usersCoursesDTO) {return enrolmentsService.getCoursesByUser(usersCoursesDTO);}

    //De testat -- n am putut testa
    @PostMapping(path = "enrolToCourse")
    public ResponseEntity<DTO> enrolStudentToCourse(@RequestBody EnrolStudentDTO enrolStudentDTO) {return enrolmentsService.enrolStudentToCourse(enrolStudentDTO);}

}
