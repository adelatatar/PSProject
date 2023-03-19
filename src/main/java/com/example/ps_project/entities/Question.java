package com.example.ps_project.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "QUESTION")
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Integer id;

    @NotNull
    @Column(name="NAME")
    private String name;

    @NotNull
    @Column(name="QUESTION_TEXT")
    private String questionText;

    @NotNull
    @Column(name="ANSWER")
    private String answer;
}
