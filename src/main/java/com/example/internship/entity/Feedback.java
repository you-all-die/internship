package com.example.internship.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import java.sql.Timestamp;

/**
 * @author Romodin Aleksey
 */

@Entity
@Table(name = "feedback")
@Data
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date_publication")
    private Timestamp datePublication;
    @Column(name = "feedback_text")
    private String feedbackText;
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "author_id")
    private Long authorId;
    @Column(name = "author_name")
    private String authorName;
}
