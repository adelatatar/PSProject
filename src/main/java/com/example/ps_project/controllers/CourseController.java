package com.example.ps_project.controllers;

import com.example.ps_project.DTOs.CourseDTO;
import com.example.ps_project.DTOs.DTO;
import com.example.ps_project.DTOs.LectureDTO;
import com.example.ps_project.DTOs.UsersCoursesDTO;
import com.example.ps_project.entities.Course;
import com.example.ps_project.services.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/course")
@AllArgsConstructor
public class CourseController {
    private final CourseService courseService;

    /**
     * Resturneaza o lista cu toate cursurile din baza de date
     * @return
     */
    @GetMapping(path = "getAllCourses")
    public List<Course> getAllCourses(){return courseService.getAllCourses();}

    /**
     * Returneaza o lista de cursuri care fac parte dintr-o anumita categorie.
     * @param category
     * @return
     */
    @GetMapping(path = "getCoursesByCategory")
    public List<Course> getAllCoursesByCategory(String category) {return courseService.getAllCoursesByCategory(category);}

    /**
     * Se realizeaza adaugarea unui nou curs in baza de date. Daca cursul nu este gasit (se cauta dupa id),
     * acesta se adauga cu succes, altfel se returneaza un mesaj de eroare.
     * @param courseDTO
     * @return
     */
    @PostMapping(path = "addNewCourse")
    public ResponseEntity<DTO> addNewCourse(@RequestBody CourseDTO courseDTO){return courseService.addNewCourse(courseDTO);}

    /**
     * Stergerea unui curs dupa id. Se cauta cursul dupa id, daca este gasit acesta va fi sters si
     * se afiseaza un mesaj de succes, in caz contrar, se afiseaza un mesaj de eroare.
     * @param id
     * @return
     */
    @DeleteMapping(path = "deleteCourse")
    public ResponseEntity<DTO> deleteCourse(@RequestBody int id) {return courseService.deleteCourse(id);}

    /**
     * Se verifica daca lectia exista, in caz afirmativ se returneaza un mesaj de eroare, iar in caz negativ
     * se continua procesul cu cautarea cursului dupa idCourse. In cazul in care cursul nu exista vom primi
     * un mesaj de eroare, altfel se adauga lectia atat in tabela Lecture cat si in tabela intermediara LectureInCourses
     * si se va primi un mesaj de succes.
     * @param lectureDTO
     * @param idCourse
     * @return
     */
    @PostMapping(path = "addLecture")
    public ResponseEntity<DTO> addNewLecture(@ModelAttribute LectureDTO lectureDTO, @RequestHeader(name = "idCourse") Integer idCourse) {
        return  courseService.addNewLecture(lectureDTO, idCourse);
    }
}
