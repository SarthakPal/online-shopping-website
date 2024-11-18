package com.lld.amazon.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveProductRequest {
    private String name;
    private String description;
    private Double price;
    private Long availableItemCount;
    private Long categoryId;

}
