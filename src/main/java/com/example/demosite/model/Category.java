package com.example.demosite.model;

public class Category {
    private long id;
    private String name;
    private String subcategory;

    public Category(long id, String name, String subcategory) {
        this.id = id;
        this.name = name;
        this.subcategory = subcategory;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }
}
