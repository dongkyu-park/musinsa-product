package com.musinsa.product.dto;

import com.musinsa.product.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfo {

    private Long id;
    private Category category;
    private String brand;
    private Integer price;
}
