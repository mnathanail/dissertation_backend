package com.dissertation.backend.node.shared;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonPropertyOrder({
        "startYear",
        "startMonth",
        "endYear",
        "endMonth"
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Period {

    @JsonProperty("startYear")
    public String startYear;
    @JsonProperty("startMonth")
    public String startMonth;
    @JsonProperty("endYear")
    public String endYear;
    @JsonProperty("endMonth")
    public String endMonth;

}