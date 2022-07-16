package com.musinsa.product.domain;

import com.musinsa.product.dto.ProductInfo;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class CategoryProductStatistic {

    private final Map<Category, ProductInfo> lowestProductInfoInCategory = new HashMap<>();
    private final Map<Category, ProductInfo> highestProductInfoInCategory = new HashMap<>();
}
