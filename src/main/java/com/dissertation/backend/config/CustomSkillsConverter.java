package com.dissertation.backend.config;

import com.dissertation.backend.node.SkillNode;
import org.neo4j.driver.Value;
import org.neo4j.driver.Values;
import org.neo4j.driver.internal.value.NodeValue;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.core.convert.support.DefaultConversionService;

import java.util.HashSet;
import java.util.Set;

public class CustomSkillsConverter implements GenericConverter {
    private static final DefaultConversionService DEFAULT_CONVERSION_SERVICE = new DefaultConversionService();

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        Set<ConvertiblePair> convertiblePairs = new HashSet<>();
        convertiblePairs.add(new ConvertiblePair(SkillNode.class, Value.class));
        convertiblePairs.add(new ConvertiblePair(Value.class, SkillNode.class));
        return convertiblePairs;
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        //(SkillNode.class.isAssignableFrom(sourceType.getType()))
        if (NodeValue.class.isAssignableFrom(sourceType.getType())) {
            return com.dissertation.backend.config.CustomType.of(((NodeValue) source).asList());
        } else {
            return Values.value(((com.dissertation.backend.config.CustomType) source).getValue());
        }
    }



    /*private Object convertToNeo4jValue(Object source) {
        SkillNode skillNode = (SkillNode) source;
        Object[] targetCollection = source.stream().map(element ->
                conversionService.convert(element, Value.class)).toArray();
        driverValue = Values.value(targetCollection);
    }

    private Object convertToMyCustomType(Object source) {
    }*/



}
