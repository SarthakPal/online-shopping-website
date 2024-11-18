package com.lld.amazon.service;

import com.lld.amazon.entity.Product;
import com.lld.amazon.entity.ProductReview;
import com.lld.amazon.entity.Users;
import com.lld.amazon.repository.ProductRepository;
import com.lld.amazon.repository.ProductReviewRepository;
import com.lld.amazon.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class ProductReviewService {

    @Autowired
    private ProductReviewRepository productReviewRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UsersRepository userRepository;

    public ProductReview addReview(Long productId, Long userId, int rating, String reviewText, byte[] image) {
        // Fetch the product
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));

        // Fetch the user
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        // Create a new ProductReview
        ProductReview productReview = new ProductReview();
        productReview.setRating(rating);
        productReview.setReview(reviewText);
        productReview.setImage(image);
        productReview.setCreationDate(LocalDateTime.now());
        productReview.setProduct(product);
        productReview.setUser(user);

        return productReviewRepository.save(productReview);
    }
}

