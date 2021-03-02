package com.dissertation.backend;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.core.Neo4jClient;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);

    }

    @Bean("graphDriver")
    public Neo4jClient getGraphDriver() {
        Driver driver = GraphDatabase
                .driver("bolt://localhost:11006", AuthTokens.basic("neo4j", "123456789"));

        return Neo4jClient.create(driver);
    }

}

/*	@Bean
	public Neo4jConversions neo4jConversions() {
		Set<GenericConverter> additionalConverters = Collections.singleton(new PeriodConverter());
		return new Neo4jConversions(additionalConverters);
	}*/

