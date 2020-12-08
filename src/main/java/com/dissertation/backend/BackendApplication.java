package com.dissertation.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

}

/*	@Bean
	public Neo4jConversions neo4jConversions() {
		Set<GenericConverter> additionalConverters = Collections.singleton(new PeriodConverter());
		return new Neo4jConversions(additionalConverters);
	}*/

