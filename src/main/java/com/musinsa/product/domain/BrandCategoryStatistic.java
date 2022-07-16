package com.musinsa.product.domain;

import com.musinsa.product.dto.ProductDto;
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

    public ProductDto getLowestPriceByBrandAndCategory(String brand, Category category) {
        return getLowestProductInfoInCategory(brand).get(category);
    }

    private boolean hasCategory(String brand, Category category) {
        return getLowestProductInfoInCategory(brand).containsKey(category);
    }

    private boolean hasBrand(String brand) {
        return priceStatisticByCategoryInBrand.containsKey(brand);
    }

    private Map<Category, ProductDto> getLowestProductInfoInCategory(String brand) {
        return priceStatisticByCategoryInBrand.get(brand).getLowestProductInfoInCategory();
    }
}
