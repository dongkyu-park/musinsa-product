package com.musinsa.product.domain;

import lombok.Getter;

import java.util.List;

@Getter
public class CategoryProductStatistic {

    private CategoryProductTag lowestPriceProduct = new CategoryProductTag();
    private CategoryProductTag highestPriceProduct = new CategoryProductTag();

    public boolean isCached(Category category) {
        if (hasCategory(category)) {
            return true;
        }

        return false;
    }

    public boolean isNotCached(Category category) {
        if (!hasCategory(category)) {
            return true;
        }

        return false;
    }

    public void comparePriceProductInfo(ProductInfo productInfo) {
        if (isLowestPriceProduct(productInfo)) {
            changeLowestPriceProductInfo(productInfo);
        }

        if (isHighestPriceProduct(productInfo)) {
            changeHighestPriceProductInfo(productInfo);
        }
    }

    private boolean isLowestPriceProduct(ProductInfo productInfo) {
        if (getLowestPriceProductInfoByCategory(productInfo.getCategory()).getPrice() > productInfo.getPrice()) {
            return true;
        }
        return false;
    }

    private boolean isHighestPriceProduct(ProductInfo productInfo) {
        if (getHighestPriceProductInfoByCategory(productInfo.getCategory()).getPrice() < productInfo.getPrice()) {
            return true;
        }
        return false;
    }

    private void changeLowestPriceProductInfo(ProductInfo productInfo) {
        lowestPriceProduct.getCategoryProductBooks().put(productInfo.getCategory(), productInfo);
    }

    private void changeHighestPriceProductInfo(ProductInfo productInfo) {
        highestPriceProduct.getCategoryProductBooks().put(productInfo.getCategory(), productInfo);
    }

    private boolean hasCategory(Category category) {
        return lowestPriceProduct.getCategoryProductBooks().containsKey(category) || highestPriceProduct.getCategoryProductBooks().containsKey(category);
    }

    public ProductInfo getLowestPriceProductInfoByCategory(Category category) {
        return lowestPriceProduct.getCategoryProductBooks().get(category);
    }

    public ProductInfo getHighestPriceProductInfoByCategory(Category category) {
        return highestPriceProduct.getCategoryProductBooks().get(category);
    }

    public void addCache(List<ProductInfo> lowestAndHighestPriceProductInfo) {
        addNewCategory(lowestAndHighestPriceProductInfo.get(0), lowestAndHighestPriceProductInfo.get(1));
    }

    private void addNewCategory(ProductInfo lowestPriceProductInfo, ProductInfo highestPriceProductInfo) {
        changeLowestPriceProductInfo(lowestPriceProductInfo);
        changeHighestPriceProductInfo(highestPriceProductInfo);
    }
}
