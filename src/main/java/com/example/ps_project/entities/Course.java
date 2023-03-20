package com.example.ps_project.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clasa Course reprezinta cursurile care vor fi pe site si pe care studentul le poate parcurge, in functie de interesele lui.
 * Aceasta are urmatoarele caracteristici: ID(generat automat), name.
 * Clasa corespunde tabelei Course din baza de date a aplicatiei
 * Adnotarile @Getter si @Setter sunt folosite pentru a avea generate gettere si settere fara a fi scrise explicit
 */
@Entity
@Table(name = "COURSE")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Course {
    @Column(name = "ID")
    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    @Column(name="NAME")
    private String name;

//    @NotNull
//    @Column(name="LECTURES")
//    private List<Lecture> lectures;

//    @NotNull
//    @Column(name ="PREREQUISITE_COURSES")
//    private List<Course> prerequisiteCourses;
}
