package com.example.ps_project.repositories;

import com.example.ps_project.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRespository extends JpaRepository<Question, Integer> {
}
