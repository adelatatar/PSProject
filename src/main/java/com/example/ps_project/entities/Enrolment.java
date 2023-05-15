package com.example.ps_project.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clasa Enrolments are rolul de a face legatura dintre un user si cursurile la care s-a inrolat
 * Aceasta are fildurile id, user si course
 * Adnotarile @Getter si @Setter sunt folosite pentru a avea generate gettere si settere fara a fi scrise explicit
 */

@Entity
@Table(name = "Enrollments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Enrolment {
    @GeneratedValue
    @Id
    @Column(name="ID")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "userID", referencedColumnName = "ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "courseID", referencedColumnName = "ID")
    private Course course;
}
