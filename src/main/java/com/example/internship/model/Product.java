package com.example.internship.model;

public class Product {
    private long id;
    private String category;
    private String name;
    private String description;
    private String imageUrl;
    private String status;

    public Product(long id, String category, String name, String description, String imageUrl, String status) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
