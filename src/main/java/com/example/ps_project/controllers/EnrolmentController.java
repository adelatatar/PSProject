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

    /**
     * Se returneaza o lista cu toate cursurile unui anumit utilizator, daca utilizatorul exista (se cauta dupa id),
     * in caz contrar se returneaza un mesaj de eroare.
     * @param usersCoursesDTO
     * @return
     */
    @GetMapping(path = "getCoursesByUser")
    public ResponseEntity<DTO> getCoursesByUser(@RequestBody UsersCoursesDTO usersCoursesDTO) {return enrolmentsService.getCoursesByUser(usersCoursesDTO);}

    /**
     * Se realizeaza inrolarea unui urilizator la un anumit curs. Se cauta atat cursul cat si utilizatorul, in cazul in
     * care nu sunt gasiti se afiseaza un mesaj de eraoare, altfel se realizeaza irolarea acestuia la cursul dorit.
     * @param enrolStudentDTO
     * @return
     */
    @PostMapping(path = "enrolToCourse")
    public ResponseEntity<DTO> enrolStudentToCourse(@RequestBody EnrolStudentDTO enrolStudentDTO) {return enrolmentsService.enrolStudentToCourse(enrolStudentDTO);}

}
