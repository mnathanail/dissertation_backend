package com.dissertation.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "summary")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Summary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    private Long id;

    @Column(name = "summary")
    private String summary;

    //@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "candidateSummaryId")
    //@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "")

/*    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    @JsonIgnore
    private Candidate candidate;*/


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    @MapsId
    @JsonIgnore
    private Candidate candidate;

}
