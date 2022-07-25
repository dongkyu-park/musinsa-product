package com.musinsa.product.domain;

import lombok.Getter;

import java.util.List;

@Getter
public class CategoryProductStatistic {

    private CategoryProductBook lowestPriceProduct = new CategoryProductBook();
    private CategoryProductBook highestPriceProduct = new CategoryProductBook();

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
        lowestPriceProduct.getCategoryProductBook().put(productInfo.getCategory(), productInfo);
    }

    private void changeHighestPriceProductInfo(ProductInfo productInfo) {
        highestPriceProduct.getCategoryProductBook().put(productInfo.getCategory(), productInfo);
    }

    private boolean hasCategory(Category category) {
        return lowestPriceProduct.getCategoryProductBook().containsKey(category) || highestPriceProduct.getCategoryProductBook().containsKey(category);
    }

    public ProductInfo getLowestPriceProductInfoByCategory(Category category) {
        return lowestPriceProduct.getCategoryProductBook().get(category);
    }

    public ProductInfo getHighestPriceProductInfoByCategory(Category category) {
        return highestPriceProduct.getCategoryProductBook().get(category);
    }

    public void addCache(List<ProductInfo> lowestAndHighestPriceProductInfo) {
        addNewCategory(lowestAndHighestPriceProductInfo.get(0), lowestAndHighestPriceProductInfo.get(1));
    }

    private void addNewCategory(ProductInfo lowestPriceProductInfo, ProductInfo highestPriceProductInfo) {
        changeLowestPriceProductInfo(lowestPriceProductInfo);
        changeHighestPriceProductInfo(highestPriceProductInfo);
    }
}
