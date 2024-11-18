package com.lld.amazon.service;

import com.lld.amazon.dto.SaveProductRequest;
import com.lld.amazon.entity.Product;
import com.lld.amazon.entity.ProductCategory;
import com.lld.amazon.mapper.ProductMapper;
import com.lld.amazon.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product addProduct(SaveProductRequest saveProductRequest) {
        Product product = ProductMapper.INSTANCE.toProduct(saveProductRequest);
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}

