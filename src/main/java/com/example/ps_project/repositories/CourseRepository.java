package com.example.ps_project.repositories;

import com.example.ps_project.entities.Course;
import com.example.ps_project.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    List<Course> findByCategory(String category);
}
