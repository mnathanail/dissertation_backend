package com.dissertation.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "candidate")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Candidate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "profile_pic")
    private byte[] profilePic;

    @Column(name = "password", nullable = false)
    @JsonIgnore
    private String password;

/*    @OneToOne(mappedBy = "candidate", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @JsonIgnore
    private Summary summary;*/

    @OneToOne(mappedBy = "candidate", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Summary summary;

}
