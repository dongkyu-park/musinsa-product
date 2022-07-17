package com.musinsa.product.repository;

import com.musinsa.product.domain.Category;
import com.musinsa.product.domain.ProductInfo;

import java.util.List;

public interface ProductQuerydslRepository {

    ProductInfo findLowestPriceByBrandAndCategory(String brand, Category category);

    List<ProductInfo> findLowestAndHighestPriceByCategory(Category category);
}
