package com.example.ps_project;

import com.example.ps_project.DTOs.*;
import com.example.ps_project.entities.Course;
import com.example.ps_project.entities.Enrolment;
import com.example.ps_project.entities.User;
import com.example.ps_project.repositories.CourseRepository;
import com.example.ps_project.repositories.EnrolmentsRepository;
import com.example.ps_project.repositories.UserRepository;
import com.example.ps_project.services.EnrolmentsService;
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

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class EnrolmetsServiceTest {
    @Mock
    private EnrolmentsRepository enrolmentsRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private EnrolmentsService enrolmentsService;

    @Test
    public void enrolStudentToCourseTest_Success(){
        EnrolStudentDTO enrolStudentDTO = new EnrolStudentDTO(1, 1);

        User user = new User();
        user.setId(enrolStudentDTO.getUserID());
        Course course = new Course();
        course.setId(enrolStudentDTO.getCourseID());

        Enrolment enrolment = new Enrolment();
        enrolment.setCourse(course);
        enrolment.setUser(user);

        when(userRepository.findById(enrolStudentDTO.getUserID())).thenReturn(Optional.of(user));
        when(courseRepository.findById(enrolStudentDTO.getCourseID())).thenReturn(Optional.of(course));
        when(enrolmentsRepository.save(enrolment)).thenReturn(enrolment);
        when(enrolmentsRepository.findById(enrolment.getId())).thenReturn(Optional.of(enrolment));

        ResponseEntity<DTO> response = enrolmentsService.enrolStudentToCourse(enrolStudentDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(SuccessMessage.class, response.getBody().getClass());
        assertEquals("You have successfully enrolled!", ((SuccessMessage) response.getBody()).getSuccessMessage());

        verify(enrolmentsRepository, times(1)).findById(enrolment.getId());
        verify(courseRepository, times(1)).findById(enrolStudentDTO.getCourseID());
    }

    @Test
    public void enrolStudentToCourseTest_NoUser(){
        EnrolStudentDTO enrolStudentDTO = new EnrolStudentDTO(1, 1);

        User user = new User();
        user.setId(enrolStudentDTO.getUserID());
        Course course = new Course();

        when(userRepository.findById(enrolStudentDTO.getUserID())).thenReturn(Optional.empty());

        ResponseEntity<DTO> response = enrolmentsService.enrolStudentToCourse(enrolStudentDTO);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ErrorDTO.class, response.getBody().getClass());
        assertEquals("The user doesn't exist!", ((ErrorDTO) response.getBody()).getErrorMessage());

        verify(userRepository, times(1)).findById(enrolStudentDTO.getCourseID());
    }

    @Test
    public void enrolStudentToCourseTest_NoCourse(){
        EnrolStudentDTO enrolStudentDTO = new EnrolStudentDTO(1, 1);

        User user = new User();
        user.setId(enrolStudentDTO.getUserID());
        Course course = new Course();
        course.setId(enrolStudentDTO.getCourseID());

        when(userRepository.findById(enrolStudentDTO.getUserID())).thenReturn(Optional.of(user));
        when(courseRepository.findById(enrolStudentDTO.getCourseID())).thenReturn(Optional.empty());

        ResponseEntity<DTO> response = enrolmentsService.enrolStudentToCourse(enrolStudentDTO);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ErrorDTO.class, response.getBody().getClass());
        assertEquals("The course doesn't exist", ((ErrorDTO) response.getBody()).getErrorMessage());

        verify(courseRepository, times(1)).findById(enrolStudentDTO.getCourseID());
        verify(userRepository, times(1)).findById(enrolStudentDTO.getCourseID());
    }
}
