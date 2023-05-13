package com.example.ps_project.services;

import com.example.ps_project.DTOs.*;
import com.example.ps_project.entities.Course;
import com.example.ps_project.entities.Lecture;
import com.example.ps_project.entities.LecturesInCourses;
import com.example.ps_project.repositories.CourseRepository;
import com.example.ps_project.repositories.LectureRespository;
import com.example.ps_project.repositories.LecturesInCoursesRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final LecturesInCoursesRepository lecturesInCoursesRepository;
    private final LectureRespository lectureRespository;

    public List<Course> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses;
    }

    public ResponseEntity<DTO> addNewCourse(CourseDTO courseDTO) {
        Optional<Course> foundCourse = courseRepository.findById(courseDTO.getId());
        if (foundCourse.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO("The course already exists!"));
        }
        Course course = new Course();
        //course.setId(courseDTO.getId());
        course.setName(courseDTO.getName());
        courseRepository.save(course);

        if (courseRepository.findById(course.getId()).isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessMessage("The course was successfully added!"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ErrorDTO("The course was not added!"));
        }
    }

    public ResponseEntity<DTO> deleteCourse(int id) {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isPresent()) {
            courseRepository.deleteById(id);
            List<LecturesInCourses> lecturesInCoursesList = lecturesInCoursesRepository.findAllByCourse(course.get());
            for (LecturesInCourses lecturesInCourses : lecturesInCoursesList) {
                lecturesInCoursesRepository.delete(lecturesInCourses);
                lectureRespository.deleteById(lecturesInCourses.getLecture().getId());
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO("The course doesn't exist!"));
        }

        if (courseRepository.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorDTO("The course has not been deleted!"));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessMessage("The course was deleted!"));
        }
    }

    public ResponseEntity<DTO> addNewLecture(LectureDTO lectureDTO, Integer idCourse) {
        Optional<Lecture> lecture = lectureRespository.findById(lectureDTO.getId());
        if (lecture.isEmpty()) {
            Lecture newLecture = new Lecture();
            newLecture.setId(lectureDTO.getId());
            newLecture.setName(lectureDTO.getName());
            try {
                newLecture.setContent(lectureDTO.getFile().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Lecture savedLecture = lectureRespository.save(newLecture);
            Optional<Course> foundCourse = courseRepository.findById(idCourse);
            if (foundCourse.isEmpty()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorDTO("The course doesn't exist!"));
            }
            Course course = foundCourse.get();
            LecturesInCourses lecturesInCourses = new LecturesInCourses();
            lecturesInCourses.setLecture(savedLecture);
            lecturesInCourses.setCourse(course);
            lecturesInCoursesRepository.save(lecturesInCourses);
            if (lecturesInCoursesRepository.findById(lecturesInCourses.getId()).isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ErrorDTO("The lecture has not been added in the course!"));
            }
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessMessage("Lecture saved!"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO("Lecture already exist!"));
        }
    }
}
