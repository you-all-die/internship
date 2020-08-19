package com.example.carcassinternapp.data.model;

import javax.persistence.*;

@Entity
public class TextData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String value;

    public TextData(){
    }

    public TextData(String value) {
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
