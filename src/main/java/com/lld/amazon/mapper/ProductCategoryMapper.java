package com.lld.amazon.mapper;

import com.lld.amazon.dto.SaveProductCategoryRequest;
import com.lld.amazon.dto.SaveProductRequest;
import com.lld.amazon.entity.Product;
import com.lld.amazon.entity.ProductCategory;
import com.lld.amazon.service.ProductCategoryService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = ProductCategoryMapperHelper.class)
public interface ProductCategoryMapper {

    ProductCategoryMapper INSTANCE = Mappers.getMapper(ProductCategoryMapper.class);

    ProductCategory toProductCategory(SaveProductCategoryRequest saveProductCategoryRequest);

}




