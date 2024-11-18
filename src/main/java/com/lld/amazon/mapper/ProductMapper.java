package com.lld.amazon.mapper;

import com.lld.amazon.dto.SaveProductRequest;
import com.lld.amazon.entity.Product;
import com.lld.amazon.entity.ProductCategory;
import com.lld.amazon.service.ProductCategoryService;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = ProductCategoryMapperHelper.class)
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "categoryId", target = "category", qualifiedByName = "mapCategoryIdToCategory")
    Product toProduct(SaveProductRequest saveProductRequest);
}

@Component
class ProductCategoryMapperHelper {

    @Autowired
    private ProductCategoryService productCategoryService;

    @Named("mapCategoryIdToCategory")
    public ProductCategory mapCategoryIdToCategory(Long categoryId) {
        return productCategoryService.getCategoryById(categoryId).get();
    }


}

