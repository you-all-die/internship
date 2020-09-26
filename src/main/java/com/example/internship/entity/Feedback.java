package com.example.internship.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

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
    private Date datePublication;
    @Column(name = "feedback_text")
    private String feedbackText;
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "author_id")
    private Long authorId;
    @Column(name = "author_name")
    private String authorName;
}
