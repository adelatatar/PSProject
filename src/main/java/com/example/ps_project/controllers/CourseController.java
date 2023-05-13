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

    @GetMapping(path = "getAllCourses")
    public List<Course> getAllCourses(){return courseService.getAllCourses();}

    @PostMapping(path = "addNewCourse")
    public ResponseEntity<DTO> addNewCourse(@RequestBody CourseDTO courseDTO){return courseService.addNewCourse(courseDTO);}

    @DeleteMapping(path = "deleteCourse")
    public ResponseEntity<DTO> deleteCourse(@RequestBody int id) {return courseService.deleteCourse(id);}

    @PostMapping(path = "addLecture")
    public ResponseEntity<DTO> addNewLecture(@ModelAttribute LectureDTO lectureDTO, @RequestHeader(name = "idCourse") Integer idCourse) {
        return  courseService.addNewLecture(lectureDTO, idCourse);
    }
}
