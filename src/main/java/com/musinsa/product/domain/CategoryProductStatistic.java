package com.musinsa.product.domain;

import com.musinsa.product.dto.ProductDto;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class CategoryProductStatistic {

    private final Map<Category, ProductDto> lowestProductInfoInCategory = new HashMap<>();
    private final Map<Category, ProductDto> highestProductInfoInCategory = new HashMap<>();
}
