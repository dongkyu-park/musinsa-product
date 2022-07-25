package com.musinsa.product.domain;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class BrandCategoryBook {

    private Map<String, CategoryProductBook> brandCategoryBook = new HashMap<>();
}
