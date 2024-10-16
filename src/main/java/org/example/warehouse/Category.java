package org.example.warehouse;

import java.util.Objects;
import java.util.HashMap;
import java.util.Map;

public class Category {
    private static final Map<String, Category> instances = new HashMap<>();
    private final String name;

    private Category(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Category name can't be null");
        }
        this.name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    public static Category of(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Category name can't be null");
        }
        return instances.computeIfAbsent(name, Category::new);
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(name,category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}