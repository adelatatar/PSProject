package com.example.ps_project.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Clasa User resprezinta cele trei tipuri de useri care se pot loga -- student, profesor, admin
 * Acestia sunt caracterizati de un ID (care se genereaza automat), nume, prenume, varsta, parola si email.
 * Clasa corespunde tabelei User din baza de date a aplicatiei
 * Adnotarile @Getter si @Setter sunt folosite pentru a avea generate gettere si settere fara a fi scrise explicit
 * Pentru email am folosit un regex care impune introducerea unui email cu format valid.
 */
@Entity
@Table(name = "USER")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Column(name = "ID")
    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    @Column(name="FIRST_NAME")
    private String firstName;

    @NotNull
    @Column(name="LAST_NAME")
    private String lastName;

    @NotNull
    @Column(name = "AGE")
    private Integer age;

    @NotNull
    @Column(name = "PASSWORD")
    private String password;

    @NotNull
    @Column(name = "EMAIL", unique = true)
    @Pattern(regexp = "\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})")
    private String email;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Enrolment> enrollments = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UsersRole> usersRoles = new HashSet<>();
}
