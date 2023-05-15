package com.example.ps_project;

import com.example.ps_project.DTOs.*;
import com.example.ps_project.entities.Course;
import com.example.ps_project.entities.Lecture;
import com.example.ps_project.entities.LecturesInCourses;
import com.example.ps_project.entities.User;
import com.example.ps_project.repositories.CourseRepository;
import com.example.ps_project.repositories.LectureRespository;
import com.example.ps_project.repositories.LecturesInCoursesRepository;
import com.example.ps_project.services.CourseService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CourseServiceTest {
    @Mock
    private CourseRepository courseRepository;

    @Mock
    private LecturesInCoursesRepository lecturesInCoursesRepository;

    @Mock
    private LectureRespository lectureRespository;

    @InjectMocks
    private CourseService courseService;

    private List<Course> courseList;

    @BeforeEach
    public void setup() {
        courseList = new ArrayList<>();
        courseList.add(new Course(1, "Java Course", null, null));
        courseList.add(new Course(2, "Phython Course", null, null));
        courseList.add(new Course(3, "C Course", null, null));
    }

    @Test
    public void getCoursesTest() {
        when(courseRepository.findAll()).thenReturn(courseList);

        List<Course> courses = courseService.getAllCourses();

        assertEquals(courseList, courses);
    }

    @Test
    public void testAddNewCourse_Success() {
        CourseDTO courseDTO = new CourseDTO(56, "English");

        Course course = new Course();
        course.setName(courseDTO.getName());

        when(courseRepository.findById(courseDTO.getId())).thenReturn(Optional.empty());
        when(courseRepository.save(Mockito.any(Course.class))).thenReturn(course);
        when(courseRepository.existsById(course.getId())).thenReturn(true);

        ResponseEntity<DTO> result = courseService.addNewCourse(courseDTO);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("The course was successfully added!", ((SuccessMessage) result.getBody()).getSuccessMessage());

        verify(courseRepository, times(1)).findById(courseDTO.getId());
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    public void testAddNewCourse_ExistingCourse() {
        CourseDTO courseDTO = new CourseDTO(56, "English");

        Course course = new Course();
        course.setName(courseDTO.getName());

        when(courseRepository.findById(courseDTO.getId())).thenReturn(Optional.of(course));

        ResponseEntity<DTO> result = courseService.addNewCourse(courseDTO);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("The course already exists!", ((ErrorDTO) result.getBody()).getErrorMessage());

        verify(courseRepository, times(1)).findById(courseDTO.getId());
    }

    @Test
    public void testAddNewCourse_NotAdded() {
        CourseDTO courseDTO = new CourseDTO(56, "English");

        Course course = new Course();
        course.setName(courseDTO.getName());

        when(courseRepository.findById(courseDTO.getId())).thenReturn(Optional.empty());
        when(courseRepository.save(Mockito.any(Course.class))).thenReturn(course);
        when(courseRepository.existsById(course.getId())).thenReturn(false);

        ResponseEntity<DTO> result = courseService.addNewCourse(courseDTO);

        assertEquals(HttpStatus.BAD_GATEWAY, result.getStatusCode());
        assertEquals("The course was not added!", ((ErrorDTO) result.getBody()).getErrorMessage());

        verify(courseRepository, times(1)).findById(courseDTO.getId());
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    public void testDeleteCourse_Success() {
        int courseId = 1;
        Course course = new Course();
        course.setId(courseId);
        List<LecturesInCourses> lecturesInCourses = new ArrayList<>();
        lecturesInCourses.add(new LecturesInCourses(1, new Lecture(1, "Lecture1", null, null), course));
        lecturesInCourses.add(new LecturesInCourses(2, new Lecture(2, "Lecture2", null, null), course));
        lecturesInCourses.add(new LecturesInCourses(3, new Lecture(3, "Lecture3", null, null), course));

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(lecturesInCoursesRepository.findAllByCourse(course)).thenReturn(lecturesInCourses);
        when(courseRepository.existsById(courseId)).thenReturn(false);

        ResponseEntity<DTO> result = courseService.deleteCourse(courseId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(SuccessMessage.class, result.getBody().getClass());
        assertEquals("The course was deleted!", ((SuccessMessage) result.getBody()).getSuccessMessage());

        verify(courseRepository, times(1)).deleteById(courseId);
        verify(courseRepository, times(1)).findById(courseId);
        verify(lecturesInCoursesRepository, times(1)).findAllByCourse(course);
        verify(courseRepository, times(1)).existsById(courseId);
    }

    @Test
    public void testDeleteCourse_NoCourse() {
        int courseId = 1;
        Course course = new Course();
        course.setId(courseId);

        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        ResponseEntity<DTO> result = courseService.deleteCourse(courseId);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals(ErrorDTO.class, result.getBody().getClass());
        assertEquals("The course doesn't exist!", ((ErrorDTO) result.getBody()).getErrorMessage());

        verify(courseRepository, times(1)).findById(courseId);
    }

    @Test
    public void testDeleteCourse_NotDeleted() {
        int courseId = 1;
        Course course = new Course();
        course.setId(courseId);
        List<LecturesInCourses> lecturesInCourses = new ArrayList<>();
        lecturesInCourses.add(new LecturesInCourses(1, new Lecture(1, "Lecture1", null, null), course));
        lecturesInCourses.add(new LecturesInCourses(2, new Lecture(2, "Lecture2", null, null), course));
        lecturesInCourses.add(new LecturesInCourses(3, new Lecture(3, "Lecture3", null, null), course));

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(lecturesInCoursesRepository.findAllByCourse(course)).thenReturn(lecturesInCourses);
        when(courseRepository.existsById(courseId)).thenReturn(true);

        ResponseEntity<DTO> result = courseService.deleteCourse(courseId);

        assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
        assertEquals(ErrorDTO.class, result.getBody().getClass());
        assertEquals("The course has not been deleted!", ((ErrorDTO) result.getBody()).getErrorMessage());

        verify(courseRepository, times(1)).deleteById(courseId);
        verify(courseRepository, times(1)).findById(courseId);
        verify(lecturesInCoursesRepository, times(1)).findAllByCourse(course);
        verify(courseRepository, times(1)).existsById(courseId);
    }

    @Test
    public void addNewLectureTest_Success() throws IOException {
        LectureDTO lectureDTO = new LectureDTO();
        lectureDTO.setName("Lecture1");
        lectureDTO.setId(1);

        MultipartFile mockFile = Mockito.mock(MultipartFile.class);
        lectureDTO.setFile(mockFile);

        int courseId = 1;

        Lecture lecture = new Lecture();
        lecture.setName(lecture.getName());
        lecture.setContent(null);

        Course course = new Course();
        course.setId(courseId);
        course.setName("Course1");

        LecturesInCourses lecturesInCourses = new LecturesInCourses();
        lecturesInCourses.setCourse(course);
        lecturesInCourses.setLecture(lecture);

        byte[] mockFileContent = "Sample file content".getBytes();
        when(mockFile.getBytes()).thenReturn(mockFileContent);
        when(lectureRespository.findById(lectureDTO.getId())).thenReturn(Optional.empty());
        //when(lectureRespository.save(lecture)).thenReturn(lecture);
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        //when(lecturesInCoursesRepository.save(lecturesInCourses)).thenReturn(lecturesInCourses);
        when(lecturesInCoursesRepository.findById(lecturesInCourses.getId())).thenReturn(Optional.of(lecturesInCourses));

        ResponseEntity<DTO> response = courseService.addNewLecture(lectureDTO, courseId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(SuccessMessage.class, response.getBody().getClass());
        assertEquals("Lecture saved!", ((SuccessMessage) response.getBody()).getSuccessMessage());
    }

    @Test
    public void addNewLectureTest_ExistingLecture() throws IOException {
        LectureDTO lectureDTO = new LectureDTO();
        lectureDTO.setName("Lecture1");
        lectureDTO.setId(1);

        MultipartFile mockFile = Mockito.mock(MultipartFile.class);
        lectureDTO.setFile(mockFile);

        int courseId = 1;

        Lecture lecture = new Lecture();
        lecture.setName(lecture.getName());
        lecture.setContent(null);

        byte[] mockFileContent = "Sample file content".getBytes();
        //when(mockFile.getBytes()).thenReturn(mockFileContent);
        when(lectureRespository.findById(lectureDTO.getId())).thenReturn(Optional.of(lecture));

        ResponseEntity<DTO> response = courseService.addNewLecture(lectureDTO, courseId);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ErrorDTO.class, response.getBody().getClass());
        assertEquals("Lecture already exist!", ((ErrorDTO) response.getBody()).getErrorMessage());
    }

    @Test
    public void addNewLectureTest_ExistingCourse() throws IOException {
        LectureDTO lectureDTO = new LectureDTO();
        lectureDTO.setName("Lecture1");
        lectureDTO.setId(1);

        MultipartFile mockFile = Mockito.mock(MultipartFile.class);
        lectureDTO.setFile(mockFile);

        int courseId = 1;

        Lecture lecture = new Lecture();
        lecture.setName(lecture.getName());
        lecture.setContent(null);

        Course course = new Course();
        course.setId(courseId);
        course.setName("Course1");

        byte[] mockFileContent = "Sample file content".getBytes();
        when(mockFile.getBytes()).thenReturn(mockFileContent);
        when(lectureRespository.findById(lectureDTO.getId())).thenReturn(Optional.empty());
        //when(lectureRespository.save(lecture)).thenReturn(lecture);
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        ResponseEntity<DTO> response = courseService.addNewLecture(lectureDTO, courseId);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(ErrorDTO.class, response.getBody().getClass());
        assertEquals("The course doesn't exist!", ((ErrorDTO) response.getBody()).getErrorMessage());
    }

    @Test
    public void addNewLectureTest_LectureNotAdded() throws IOException {
        LectureDTO lectureDTO = new LectureDTO();
        lectureDTO.setName("Lecture1");
        lectureDTO.setId(1);

        MultipartFile mockFile = Mockito.mock(MultipartFile.class);
        lectureDTO.setFile(mockFile);

        int courseId = 1;

        Lecture lecture = new Lecture();
        lecture.setName(lecture.getName());
        lecture.setContent(null);

        Course course = new Course();
        course.setId(courseId);
        course.setName("Course1");

        LecturesInCourses lecturesInCourses = new LecturesInCourses();
        lecturesInCourses.setCourse(course);
        lecturesInCourses.setLecture(lecture);

        byte[] mockFileContent = "Sample file content".getBytes();
        when(mockFile.getBytes()).thenReturn(mockFileContent);
        when(lectureRespository.findById(lectureDTO.getId())).thenReturn(Optional.empty());
        //when(lectureRespository.save(lecture)).thenReturn(lecture);
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        //when(lecturesInCoursesRepository.save(lecturesInCourses)).thenReturn(lecturesInCourses);
        when(lecturesInCoursesRepository.findById(lecturesInCourses.getId())).thenReturn(Optional.empty());

        ResponseEntity<DTO> response = courseService.addNewLecture(lectureDTO, courseId);
        assertEquals(HttpStatus.BAD_GATEWAY, response.getStatusCode());
        assertEquals(ErrorDTO.class, response.getBody().getClass());
        assertEquals("The lecture has not been added in the course!", ((ErrorDTO) response.getBody()).getErrorMessage());
    }
}

