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

/**
 * Clasa LectureService contine toate metodele/endpoint-urile necesare pentru functionalitatile dorite in aplicatia la care lucrez
 */
@Service
@AllArgsConstructor
public class LectureService {
    private final LectureRespository lectureRespository;
    private final LecturesInCoursesRepository lecturesInCoursesRepository;

    /**
     * Se realizeaza stergerea unei anumite lectii in functie de id-ul acesteia. Prima data se cauta lectia,
     * daca nu se gaseste se returneaza un mesaj de eroare, iar in cazul in care se gaseste se sterge atat din tabela
     * Lecture, cat si toate aparitiile din tabela LectureInCourses.
     * @param idLecture
     * @return
     */
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
