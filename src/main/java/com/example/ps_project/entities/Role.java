package com.example.ps_project.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "ROLE")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Role {
    @Column(name = "ID")
    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    @Column(name = "NAME")
    @Enumerated(EnumType.STRING)
    private RoleEnum name;
}
