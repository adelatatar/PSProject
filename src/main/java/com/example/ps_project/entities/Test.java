package com.example.ps_project.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clasa Test reprezinta Testele ce vor fi la finalul fiecarui curs.
 * Aceasta este caracterizata de ID (generat automat) si nume.
 * Clasa corespunde tabelei Test din baza de date a aplicatiei.
 * Adnotarile @Getter si @Setter sunt folosite pentru a avea generate gettere si settere fara a fi scrise explicit.
 */
@Entity
@Table(name = "TEST")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Test {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Integer id;

    @NotNull
    @Column(name = "NAME")
    private String name;
}
