package com.dissertation.backend.node.converters;


import com.dissertation.backend.node.shared.Period;
import org.neo4j.driver.Value;
import org.neo4j.driver.Values;
import org.springframework.data.neo4j.core.convert.Neo4jConversionService;
import org.springframework.data.neo4j.core.convert.Neo4jPersistentPropertyToMapConverter;

import java.util.HashMap;
import java.util.Map;

public class PeriodConverter implements Neo4jPersistentPropertyToMapConverter<String, Period> {

    @Override
    public Map<String, Value> decompose(Period period, Neo4jConversionService neo4jConversionService) {

        final HashMap<String, Value> decomposed = new HashMap<>();
        decomposed.put("startYear", Values.value(period.getStartYear()));
        decomposed.put("startMonth", Values.value(period.getStartMonth()));
        decomposed.put("endYear", Values.value(period.getEndYear()));
        decomposed.put("endMonth", Values.value(period.getEndMonth()));
        return decomposed;

    }

    @Override
    public Period compose(Map<String, Value> source, Neo4jConversionService neo4jConversionService) {
        return source.isEmpty() ?
            null : new Period(source.get("startYear").asString(),
                        source.get("startMonth").asString(),
                        source.get("endYear").asString(),
                        source.get("endMonth").asString()
                    );

    }
}
