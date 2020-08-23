package com.example.demosite.repository;

import com.example.demosite.model.Category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryRepository {
    private final Map<Long, Category> categories;
    private long index;

    public CategoryRepository() {
        categories = new HashMap<>();
        categories.put(0L, new Category(0, "Смартфоны и гаджеты", ""));
        categories.put(1L, new Category(1, "Смартфоны", "Смартфоны и гаджеты"));
        categories.put(2L, new Category(2, "Планшеты", "Смартфоны и гаджеты"));
        categories.put(3L, new Category(3, "Ноутбуки и компьютеры", ""));
        categories.put(4L, new Category(4, "Ноутбуки", "Ноутбуки и компьютеры"));

        index = 5;
    }

    public Category getCategoryById(String id) {
        return categories.get(Long.parseLong(id));
    }

    public List<Category> findAll() {
        return new ArrayList<>(categories.values());
    }

    public void removeCategory(String id) {
        categories.remove(Long.parseLong(id));
    }

    public void addCategory(Category product) {
        product.setId(index);
        categories.put(index++, product);
    }

    public List<Category> findByName(String name) {
        List<Category> result = new ArrayList<>();

        categories.forEach((id, category) -> {
            if (category.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(category);
            }
        });
        return result;
    }
}
