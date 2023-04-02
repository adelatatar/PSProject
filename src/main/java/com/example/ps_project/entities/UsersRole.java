package com.example.ps_project.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "UsersRole")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UsersRole {
    @GeneratedValue
    @Id
    @Column(name="ID")
    private Integer id;

    @NotNull
    @Column(name="USERID")
    private Integer userID;

    @NotNull
    @Column(name="ROLEID")
    private Integer roleID;
}
