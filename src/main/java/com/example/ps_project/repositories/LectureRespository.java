package com.example.ps_project.repositories;

import com.example.ps_project.entities.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureRespository extends JpaRepository<Lecture, Integer> {

}
