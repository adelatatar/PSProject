package com.example.ps_project.repositories;

import com.example.ps_project.entities.QuestionsInTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionsInTestsRepository extends JpaRepository<QuestionsInTest, Integer> {
}
