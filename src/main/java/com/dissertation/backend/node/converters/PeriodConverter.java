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


/*
    @Override
    public Object compose(Map source, Neo4jConversionService neo4jConversionService) {
        String startYear = (String) source.get("startYear");
        String startMonth = (String) source.get("startMonth");
        String endYear = (String) source.get("endYear");
        String endMonth = (String) source.get("endMonth");

        if (startYear != null && startMonth != null && endYear != null && endMonth != null) {
            return new Period(startYear, startMonth, endYear, endMonth);
        }
        return null;
    }

    @Override
    public Map<Period, Value> decompose(String property, Neo4jConversionService neo4jConversionService) {
        Map<Period, Value> properties = new HashMap<>();
        //Period period = (Period) property;
        properties.put("startYear",  );
        properties.put("startMonth", period.getStartMonth());
        properties.put("endYear",    period.getEndYear());
        properties.put("endMonth",   period.getEndMonth());
        return properties;
    }
 */