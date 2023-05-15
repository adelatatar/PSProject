package com.example.ps_project.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clasa QuestionsInTest are rolul de a face legatura intre teste si intrebarile care sunt la fiecare test
 * Aceasta contine un id, un test si o intrebare
 */

@Entity
@Table(name = "QuestionsInTests")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuestionsInTest {
    @GeneratedValue
    @Id
    @Column(name="ID")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "questionID", referencedColumnName = "ID")
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "testID", referencedColumnName = "ID")
    private Test test;
}
