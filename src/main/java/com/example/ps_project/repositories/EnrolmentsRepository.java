package com.example.ps_project.repositories;

import com.example.ps_project.entities.Course;
import com.example.ps_project.entities.Enrolment;
import com.example.ps_project.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrolmentsRepository extends JpaRepository<Enrolment, Integer> {
    List<Course> getCoursesByUser(User user);
}
