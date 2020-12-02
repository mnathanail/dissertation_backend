package com.dissertation.backend.node;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Node
public class SkillNode {

    @Id
    @GeneratedValue
    private Long id;

    @JsonProperty("entityId")
    @Property(name = "entity_id")
    private Long entityId;

    @JsonProperty("name")
    @Property(name = "name")
    private String name;

/*    @Property(name = "years_of_experience")
    private Long yoe;*/

    /*@Property(name = "yoe")
    private Map<String, Object> props = new HashMap();*/

/*
    @Relationship(type = "KNOWS", direction = Relationship.Direction.INCOMING)
    @JsonIgnore
    private List<CandidateNode> nodeCandidateNodes = new ArrayList<>();

    @Relationship(type = "REQUIRES", direction = Relationship.Direction.INCOMING)
    @JsonIgnore
    private List<JobNode> jobNodes = new ArrayList<>();*/


}
/*
match (c:MainNode {id:$mainId}), (s:SubNodes)
where s.id IN $subIds
merge (c)-[r:RELATIONX]->(s)
return *;
* */