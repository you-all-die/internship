package com.ss.internetstore.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private int parent_Id;

    public Category(String name, int parent_Id) {
        this.name = name;
        this.parent_Id = parent_Id;
    }

    public Category() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParent_Id() {
        return parent_Id;
    }

    public void setParent_Id(int parent_Id) {
        this.parent_Id = parent_Id;
    }
}
