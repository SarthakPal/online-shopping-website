package com.lld.amazon.controller;

import com.lld.amazon.entity.ProductReview;
import com.lld.amazon.service.ProductReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/reviews")
public class ProductReviewController {

    @Autowired
    private ProductReviewService productReviewService;

    @PostMapping("/{productId}/add/{userId}")
    public ProductReview addReview(
            @PathVariable Long productId,
            @PathVariable Long userId,
            @RequestParam int rating,
            @RequestParam String review,
            @RequestParam(required = false) MultipartFile imageFile) {
        try {
            byte[] image = imageFile != null ? imageFile.getBytes() : null;
            return productReviewService.addReview(productId, userId, rating, review, image);
        } catch (Exception e) {
            throw new RuntimeException("Error uploading image", e);
        }
    }
}

