package com.example.intership.data.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "words")
@Data
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "value", nullable = false)
    private String value;
}
