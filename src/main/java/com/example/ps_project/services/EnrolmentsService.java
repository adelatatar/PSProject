package com.example.ps_project.services;

import com.example.ps_project.DTOs.*;
import com.example.ps_project.entities.Course;
import com.example.ps_project.entities.Enrolment;
import com.example.ps_project.entities.User;
import com.example.ps_project.repositories.CourseRepository;
import com.example.ps_project.repositories.EnrolmentsRepository;
import com.example.ps_project.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *Clasa EnrolmentsService contine toate metodele/endpoint-urile necesare pentru functionalitatile dorite in aplicatia la care lucrez
 */
@Service
@AllArgsConstructor
public class EnrolmentsService {
    private final EnrolmentsRepository enrolmentsRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    /**
     * Se returneaza o lista cu toate cursurile unui anumit utilizator, daca utilizatorul exista (se cauta dupa id),
     * in caz contrar se returneaza un mesaj de eroare.
     * @param usersCoursesDTO
     * @return
     */
    public ResponseEntity<DTO> getCoursesByUser(UsersCoursesDTO usersCoursesDTO){
        Optional<User> user = userRepository.findById(usersCoursesDTO.getId());
        if(user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO("User does not exit!"));
        }

        User foundUser = user.get();
        List<Course> courses = enrolmentsRepository.getCoursesByUser(foundUser);
        List<CourseDTO> courseDTOS = new ArrayList<>();
        for(Course currentCourse : courses) {
            CourseDTO courseDTO = new CourseDTO();
            courseDTO.setId(currentCourse.getId());
            courseDTO.setName(currentCourse.getName());
            courseDTOS.add(courseDTO);
        }
        ViewCoursesDTO viewCoursesDTO = new ViewCoursesDTO();
        viewCoursesDTO.setCourses(courseDTOS);
        return ResponseEntity.status(HttpStatus.OK).body(viewCoursesDTO);
    }

    /**
     * Se realizeaza inrolarea unui urilizator la un anumit curs. Se cauta atat cursul cat si utilizatorul, in cazul
     * in care nu sunt gasiti se afiseaza un mesaj de eraoare, altfel se realizeaza irolarea acestuia la cursul dorit.
     * @param enrolStudentDTO
     * @return
     */
    public ResponseEntity<DTO> enrolStudentToCourse(EnrolStudentDTO enrolStudentDTO) {
        Optional<User> foundUser = userRepository.findById(enrolStudentDTO.getUserID());
        if (foundUser.isPresent()) {
            User userToEnrol;
            userToEnrol = foundUser.get();
            Optional<Course> foundCourse = courseRepository.findById(enrolStudentDTO.getCourseID());
            if (foundCourse.isPresent()) {
                Course course;
                course = foundCourse.get();
                List<Enrolment> enrolments = enrolmentsRepository.findAll();
                for(Enrolment e : enrolments){
                    if(e.getUser().getId() == userToEnrol.getId() && e.getCourse().getId() == course.getId()) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO("You are already enrolled in the course!"));
                    }
                }
                Enrolment enrolment = new Enrolment();
                enrolment.setCourse(course);
                enrolment.setUser(userToEnrol);
                enrolmentsRepository.save(enrolment);
                if(enrolmentsRepository.findById(enrolment.getId()).isPresent()) {
                    return ResponseEntity.status(HttpStatus.OK).body(new SuccessMessage("You have successfully enrolled!"));
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO("The course doesn't exist"));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO("The user doesn't exist!"));
    }
}
