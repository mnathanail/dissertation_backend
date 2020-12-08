package com.dissertation.backend.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public  class CustomType {

    private final String value;
    private final String name;

    public static CustomType of(String value, String name) {
        return new CustomType(value, name);
    }

    public static Object of(List<Object> asList) {
        return new CustomType(asList.get(0).toString(), asList.get(1).toString());
    }

    public List<String> getValue() {
        List<String> a = new ArrayList<>();
        a.add(value);
        a.add(name);
        return a;
    }

    public CustomType(String value, String name) {
        this.value = value;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CustomType that = (CustomType) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}