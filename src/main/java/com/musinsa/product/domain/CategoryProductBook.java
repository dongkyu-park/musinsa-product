package com.musinsa.product.domain;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class CategoryProductBook {

    private final Map<Category, ProductInfo> categoryProductBook = new HashMap<>();
}
