package com.musinsa.product.domain;

import com.musinsa.product.dto.ProductInfo;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class BrandCategoryStatistic {

    private Map<String, CategoryProductStatistic> priceStatisticByCategoryInBrand = new HashMap<>();

    public boolean isNotCached(String brand, Category category) {
        if (!hasBrand(brand)) {
            return true;
        }

        if (!hasCategory(brand, category)) {
            return true;
        }

        return false;
    }

    public ProductInfo getLowestPriceProduct(String brand, Category category) {
        return getLowestProductInfoInCategory(brand).get(category);
    }

    private boolean hasCategory(String brand, Category category) {
        return getLowestProductInfoInCategory(brand).containsKey(category);
    }

    private boolean hasBrand(String brand) {
        return priceStatisticByCategoryInBrand.containsKey(brand);
    }

    private Map<Category, ProductInfo> getLowestProductInfoInCategory(String brand) {
        return priceStatisticByCategoryInBrand.get(brand).getLowestProductInfoInCategory();
    }
}
