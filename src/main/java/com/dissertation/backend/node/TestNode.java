package com.dissertation.backend.node;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@Node
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestNode {
    @Id
    @GeneratedValue
    Long id;

    @Property(name = "name")
    String name;

    @Property(name = "email")
    String email;

    public TestNode(String name, String email) {
    }

    public static TestNode of(String name, String email) {
        return new TestNode(name, email);
    }

    public String getValue() {
        return name;
    }

}
