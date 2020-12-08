package com.dissertation.backend.node;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GeneralSkillNode {

    @JsonProperty("entityId")
    private Long entityId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("yearsOfExperience")
    private Long yearsOfExperience;

}
