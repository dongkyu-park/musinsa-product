package com.musinsa.product.repository;

import com.musinsa.product.domain.Category;
import com.musinsa.product.dto.ProductInfo;

public interface ProductQuerydslRepository {

    ProductInfo findLowestPriceByBrandAndCategory(String brand, Category category);
}
