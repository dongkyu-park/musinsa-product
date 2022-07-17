package com.musinsa.product.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CategoryProductStatistic {

    private CategoryProductTag lowestPriceProduct = new CategoryProductTag();
    private CategoryProductTag highestPriceProduct = new CategoryProductTag();

    public boolean isNotCached(Category category) {
        if (!hasCategory(category)) {
            return true;
        }

        return false;
    }

    private boolean hasCategory(Category category) {
        return lowestPriceProduct.getCategoryProductBooks().containsKey(category) || highestPriceProduct.getCategoryProductBooks().containsKey(category);
    }

    private ProductInfo getLowestPriceProductInfo(Category category) {
        return lowestPriceProduct.getCategoryProductBooks().get(category);
    }

    private ProductInfo getHighestPriceProductInfo(Category category) {
        return highestPriceProduct.getCategoryProductBooks().get(category);
    }

    public void addCache(ProductInfo lowestPriceProductInfo, ProductInfo highestPriceProductInfo, Category category) {
        addNewCategory(lowestPriceProductInfo, highestPriceProductInfo, category);
    }

    private void addNewCategory(ProductInfo lowestPriceProductInfo, ProductInfo highestPriceProductInfo, Category category) {
        lowestPriceProduct.getCategoryProductBooks()
                .put(category, lowestPriceProductInfo);

        highestPriceProduct.getCategoryProductBooks()
                .put(category, highestPriceProductInfo);
    }

    public List<ProductInfo> getLowestAndHighestPriceProductInfoByCategory(Category category) {
        List<ProductInfo> lowestAndHighestPriceProductInfo = new ArrayList<>();
        lowestAndHighestPriceProductInfo.add(lowestPriceProduct.getCategoryProductBooks().get(category));
        lowestAndHighestPriceProductInfo.add(highestPriceProduct.getCategoryProductBooks().get(category));

        return lowestAndHighestPriceProductInfo;
    }
}
