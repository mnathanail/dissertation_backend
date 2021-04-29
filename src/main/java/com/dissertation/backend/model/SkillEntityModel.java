package com.dissertation.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SkillEntityModel {
    private Long id;
    private Long entityId;
    private String name;
    private int yearsOfExperience;
}
