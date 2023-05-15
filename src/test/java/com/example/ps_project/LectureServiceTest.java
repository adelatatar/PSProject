package com.example.ps_project;

import com.example.ps_project.DTOs.DTO;
import com.example.ps_project.DTOs.ErrorDTO;
import com.example.ps_project.DTOs.SuccessMessage;
import com.example.ps_project.entities.Lecture;
import com.example.ps_project.repositories.LectureRespository;
import com.example.ps_project.repositories.LecturesInCoursesRepository;
import com.example.ps_project.services.LectureService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Aceasta este clasa care contine testele pentru toate metodele din LectureService
 */

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class LectureServiceTest {
    @Mock
    private LectureRespository lectureRespository;

    @Mock
    private LecturesInCoursesRepository lecturesInCoursesRepository;

    @InjectMocks
    private LectureService lectureService;

    /**
     * Testeaza metoda deleteLecture din LectureService in cazul in care stergerea unei lectii se realizeaza cu succes
     */
    @Test
    public void deleteLectureTest_Success(){
        int idLecture = 1;
        Optional<Lecture> mockLecture = Optional.of(new Lecture(idLecture, "Lecture1", null, null));

        when(lectureRespository.findById(idLecture)).thenReturn(mockLecture);
        when(lectureRespository.existsById(idLecture)).thenReturn(false);

        ResponseEntity<DTO> response = lectureService.deleteLecture(idLecture);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("The lecture was deleted!", ((SuccessMessage) response.getBody()).getSuccessMessage());
        verify(lectureRespository, times(1)).deleteById(idLecture);
        verify(lectureRespository, times(1)).findById(idLecture);
        verify(lectureRespository, times(1)).existsById(idLecture);
    }

    /**
     * Testeaza metoda deleteLecture din LectureService in cazul in care stergerea unei lectii NU se realizeaza cu succes
     */
    @Test
    public void deleteLectureTest_Failed(){
        int idLecture = 1;
        Optional<Lecture> mockLecture = Optional.of(new Lecture(idLecture, "Lecture1", null, null));

        when(lectureRespository.findById(idLecture)).thenReturn(mockLecture);
        when(lectureRespository.existsById(idLecture)).thenReturn(true);

        ResponseEntity<DTO> response = lectureService.deleteLecture(idLecture);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("The lecture has not been deleted!", ((ErrorDTO) response.getBody()).getErrorMessage());
        verify(lectureRespository, times(1)).deleteById(idLecture);
        verify(lectureRespository, times(1)).findById(idLecture);
        verify(lectureRespository, times(1)).existsById(idLecture);
    }

    /**
     * Testeaza metoda deleteLecture din LectureService in cazul in care lectia pe care dorim sa o stergem nu exista
     */
    @Test
    public void deleteLectureTest_NoLecture(){
        int idLecture = 1;

        when(lectureRespository.findById(idLecture)).thenReturn(Optional.empty());

        ResponseEntity<DTO> response = lectureService.deleteLecture(idLecture);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("The lecture doesn't exist!", ((ErrorDTO) response.getBody()).getErrorMessage());
        verify(lectureRespository, times(1)).findById(idLecture);
    }
}
