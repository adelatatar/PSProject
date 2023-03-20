package com.example.ps_project.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clasa Lecture reprezinta lectiile care vor fi parcurse in fiecare curs.
 * Are urmatoarele carateristici: ID (generat automat), nume.
 * Aceasta corespunde tabelei Lecture din baza de date a aplicatiei
 * Adnotarile @Getter si @Setter sunt folosite pentru a avea generate gettere si settere fara a fi scrise explicit
 */

@Entity
@Table(name="LECTURE")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Lecture {
    @GeneratedValue
    @Id
    @Column(name="ID")
    private Integer id;

    @NotNull
    @Column(name="NAME")
    private String name;
}
