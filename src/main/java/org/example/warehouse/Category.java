package org.example.warehouse;

import java.util.Objects;
import java.util.HashMap;
import java.util.Map;

public class Category {
    private static final Map<String, Category> instances = new HashMap<>();
    private final String name;

    private Category(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Category name can't be null or empty.");
        }
        this.name = name;
    }

    public static Category getInstance(String name){
        return instances.computeIfAbsent(name, Category::new);
    }

    public static Category of(String name) {
        return new Category(name);
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return name.equals(category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}