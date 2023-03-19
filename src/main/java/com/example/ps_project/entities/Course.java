package com.example.ps_project.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "COURSE")
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @Column(name = "ID")
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
