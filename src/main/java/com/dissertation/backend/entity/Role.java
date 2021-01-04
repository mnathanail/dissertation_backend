package com.dissertation.backend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "role")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "role", nullable = false)
    private String authority;

/*    @ManyToMany(fetch = FetchType.LAZY, cascade =
            {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}
    )
    @JoinTable(
            name = "candidate_roles", joinColumns = {
            @JoinColumn(name = "role_id"),
    },
            inverseJoinColumns = {
                    @JoinColumn(name = "candidate_id")
            }
    )*/
    /*@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "roles")*/
/*    @Setter
    @Getter(onMethod = @__( @JsonBackReference))
    private Set<Candidate> candidates;*/
}
