package com.example.ps_project.services;

import com.example.ps_project.DTOs.DTO;
import com.example.ps_project.DTOs.ErrorDTO;
import com.example.ps_project.DTOs.SuccessMessage;
import com.example.ps_project.entities.Lecture;
import com.example.ps_project.entities.LecturesInCourses;
import com.example.ps_project.repositories.LectureRespository;
import com.example.ps_project.repositories.LecturesInCoursesRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LectureService {
    private final LectureRespository lectureRespository;
    private final LecturesInCoursesRepository lecturesInCoursesRepository;

    public ResponseEntity<DTO> deleteLecture(Integer idLecture) {
        Optional<Lecture> lecture = lectureRespository.findById(idLecture);
        if (lecture.isPresent()) {
            lectureRespository.deleteById(idLecture);
            List<LecturesInCourses> lecturesInCoursesList = lecturesInCoursesRepository.findAll();
            for(LecturesInCourses lecturesInCourses:lecturesInCoursesList) {
                if(lecturesInCourses.getLecture().getId() == idLecture) {
                    lecturesInCoursesRepository.delete(lecturesInCourses);
                }
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO("The lecture doesn't exist!"));
        }

        if (lectureRespository.existsById(idLecture)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorDTO("The lecture has not been deleted!"));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessMessage("The lecture was deleted!"));
        }
    }
}
