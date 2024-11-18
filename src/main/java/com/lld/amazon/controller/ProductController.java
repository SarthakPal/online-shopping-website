package com.lld.amazon.controller;

import com.lld.amazon.dto.SaveProductRequest;
import com.lld.amazon.entity.Product;
import com.lld.amazon.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    public Product addProduct(@RequestBody SaveProductRequest saveProductRequest) {
        return productService.addProduct(saveProductRequest);
    }

}

