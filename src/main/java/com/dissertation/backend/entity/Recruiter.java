package com.dissertation.backend.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "recruiter")
@Data
public class Recruiter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "profile_pic")
    private byte[] profilePic;

    @Column(name = "password", nullable = false)
    private String password;

}
