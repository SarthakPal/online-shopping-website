package com.lld.amazon.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ProductReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rating;

    @Lob  // To store larger text or binary data
    private String review;

    @Lob
    private byte[] image;

    @ManyToOne  // Many reviews can be created by one user
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne  // Many reviews belong to one product
    @JoinColumn(name = "product_id")
    private Product product;

    private LocalDateTime creationDate;

}

