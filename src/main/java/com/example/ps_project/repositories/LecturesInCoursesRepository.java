package com.example.ps_project.repositories;

import com.example.ps_project.entities.Course;
import com.example.ps_project.entities.LecturesInCourses;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LecturesInCoursesRepository extends JpaRepository<LecturesInCourses, Integer> {
    List<LecturesInCourses> findAllByCourse(Course course);
}
