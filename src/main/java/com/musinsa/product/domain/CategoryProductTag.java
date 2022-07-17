package com.musinsa.product.domain;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class CategoryProductTag {

    private final Map<Category, ProductInfo> categoryProductBooks = new HashMap<>();
}
