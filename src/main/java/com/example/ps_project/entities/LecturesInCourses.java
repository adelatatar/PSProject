package com.example.ps_project.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "LecturesInCourses")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LecturesInCourses {
    @GeneratedValue
    @Id
    @Column(name="ID")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "lectureID", referencedColumnName = "ID")
    private Lecture lecture;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "courseID", referencedColumnName = "ID")
    private Course course;
}
