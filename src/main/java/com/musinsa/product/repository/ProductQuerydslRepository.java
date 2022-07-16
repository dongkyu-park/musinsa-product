package com.musinsa.product.repository;

import com.musinsa.product.domain.Category;
import com.musinsa.product.dto.ProductDto;

public interface ProductQuerydslRepository {

    ProductDto findLowestPriceByBrandAndCategory(String brand, Category category);
}
