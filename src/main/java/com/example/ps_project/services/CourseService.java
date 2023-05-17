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

/**
 * Clasa CourseService contine toate metodele/endpoint-urile necesare pentru functionalitatile dorite in aplicatia la care lucrez
 */
@Service
@AllArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final LecturesInCoursesRepository lecturesInCoursesRepository;
    private final LectureRespository lectureRespository;

    /** Resturneaza o lista cu toate cursurile din baza de date
     * @return
     */
    public List<Course> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses;
    }

    /**
     * Se realizeaza adaugarea unui nou curs in baza de date. Daca cursul nu este gasit (se cauta dupa id),
     * acesta se adauga cu succes, altfel se returneaza un mesaj de eroare.
     * @param courseDTO
     * @return
     */
    public ResponseEntity<DTO> addNewCourse(CourseDTO courseDTO) {
        Optional<Course> foundCourse = courseRepository.findById(courseDTO.getId());
        if (foundCourse.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO("The course already exists!"));
        }
        Course course = new Course();
        course.setName(courseDTO.getName());
        Course savedCourse = courseRepository.save(course);

        if (courseRepository.existsById(savedCourse.getId())) {
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessMessage("The course was successfully added!"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ErrorDTO("The course was not added!"));
        }
    }

    /**
     * Stergerea unui curs dupa id. Se cauta cursul dupa id,
     * daca este gasit acesta va fi sters si se afiseaza un mesaj de succes, in caz contrar, se afiseaza un mesaj de eroare.
     * @param id
     * @return
     */
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

        if (courseRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorDTO("The course has not been deleted!"));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessMessage("The course was deleted!"));
        }
    }

    /**
     * Se verifica daca lectia exista, in caz afirmativ se returneaza un mesaj de eroare, iar in caz negativ se continua
     * procesul cu cautarea cursului dupa idCourse. In cazul in care cursul nu exista vom primi un mesaj de eroare,
     * altfel se adauga lectia atat in tabela Lecture cat si in tabela intermediara LectureInCourses si se va primi un mesaj de succes.
     * @param lectureDTO
     * @param idCourse
     * @return
     */
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
