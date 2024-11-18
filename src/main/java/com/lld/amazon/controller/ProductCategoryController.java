package com.lld.amazon.controller;

import com.lld.amazon.dto.SaveProductCategoryRequest;
import com.lld.amazon.dto.SaveProductRequest;
import com.lld.amazon.entity.Product;
import com.lld.amazon.entity.ProductCategory;
import com.lld.amazon.service.ProductCategoryService;
import com.lld.amazon.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product/category")
public class ProductCategoryController {
    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping
    public List<ProductCategory> getAllProductCategories() {
        return productCategoryService.getAllCategories();
    }

    @PostMapping
    public ProductCategory addProductCategory(@RequestBody SaveProductCategoryRequest saveProductCategoryRequest) {
        return productCategoryService.addCategory(saveProductCategoryRequest);
    }

}

