package com.lld.amazon.service;

import com.lld.amazon.dto.SaveProductCategoryRequest;
import com.lld.amazon.entity.ProductCategory;
import com.lld.amazon.mapper.ProductCategoryMapper;
import com.lld.amazon.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductCategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    public ProductCategory addCategory(SaveProductCategoryRequest saveProductCategoryRequest) {
        ProductCategory productCategory = ProductCategoryMapper.INSTANCE.toProductCategory(saveProductCategoryRequest);
        return productCategoryRepository.save(productCategory);
    }

    public List<ProductCategory> getAllCategories() {
        return productCategoryRepository.findAll();
    }

    public Optional<ProductCategory> getCategoryById(Long id) {
        return productCategoryRepository.findById(id);
    }
}

