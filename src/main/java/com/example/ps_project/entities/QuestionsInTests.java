package com.example.ps_project.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "QuestionsInTests")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuestionsInTests {
    @GeneratedValue
    @Id
    @Column(name="ID")
    private Integer id;

    @NotNull
    @Column(name="QUESTIONID")
    private Integer questionID;

    @NotNull
    @Column(name="TESTID")
    private Integer testID;
}
