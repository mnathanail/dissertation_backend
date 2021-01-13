package com.dissertation.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "candidate")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Candidate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "candidate_id")
    private String candidateId;

    @NotEmpty(message = "Name is required")
    @Column(name = "name", nullable = false)
    private String name;

    @NotEmpty(message = "Surname is required")
    @Column(name = "surname")
    private String surname;

    @NotEmpty(message = "Email is required")
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "profile_pic")
    private byte[] profilePic;

    @NotEmpty(message = "Password is required")
    @Column(name = "password", nullable = false)
    private String password;

    @OneToOne(mappedBy = "candidate", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Summary summary;

    @ManyToMany(fetch = FetchType.LAZY, cascade =
                {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}
            )
    @JoinTable(
            name="candidate_roles", joinColumns = {
                    @JoinColumn(name = "candidate_id"),
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "role_id")
            }
    )
    private Set<Role> authorities;
}
