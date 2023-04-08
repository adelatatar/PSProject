package com.example.ps_project.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Clasa Question reprezinta Intrebarile ce vor fi in testele de la finalul fiecarui curs.
 * Aceasta este caracterizata de ID (generat automat), nume, textul intrebarii si raspuns.
 * Clasa corespunde tabelei Question din baza de date a aplicatiei.
 * Adnotarile @Getter si @Setter sunt folosite pentru a avea generate gettere si settere fara a fi scrise explicit.
 */

@Entity
@Table(name = "QUESTION")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Question {
    @Column(name = "ID")
    @Id
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "question", cascade = CascadeType.ALL)
    private Set<QuestionsInTests> questionsInTests = new HashSet<>();
}
