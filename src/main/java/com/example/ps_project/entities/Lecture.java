package com.example.ps_project.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Entity
@Table(name="LECTURE")
@NoArgsConstructor
@AllArgsConstructor
public class Lecture {
    @Id
    @GeneratedValue
    @Column(name="ID")
    private Integer id;

    @NotNull
    @Column(name="NAME")
    private String name;
}
