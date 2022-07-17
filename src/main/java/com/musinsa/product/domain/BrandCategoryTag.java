package com.musinsa.product.domain;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class BrandCategoryTag {

    private Map<String, CategoryProductTag> brandCategoryBooks = new HashMap<>();
}
