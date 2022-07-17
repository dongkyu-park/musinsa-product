package com.musinsa.product.domain;

import lombok.Getter;

import java.util.Map;

@Getter
public class BrandProductStatistic {

    private BrandCategoryTag lowestPriceCategories = new BrandCategoryTag();

    public boolean isCached(String brand, Category category) {
        if (hasBrand(brand) && hasCategory(brand, category)) {
            return true;
        }

        return false;
    }

    public boolean isNotCached(String brand, Category category) {
        if (!hasBrand(brand)) {
            return true;
        }

        if (!hasCategory(brand, category)) {
            return true;
        }

        return false;
    }

    public void compareLowestPriceProductInfo(ProductInfo productInfo) {
        if (isLowestPriceProduct(productInfo)) {
            changeLowestPriceProductInfo(productInfo);
        }
    }

    public void addCache(ProductInfo productInfo) {
        if (hasBrand(productInfo.getBrand())) {
            addNewCategory(productInfo);
            return;
        }
        addNewBrand(productInfo);
        addNewCategory(productInfo);
    }

    public ProductInfo getLowestPriceProductInfo(String brand, Category category) {
        return getLowestPriceProductBooks(brand).get(category);
    }

    private boolean isLowestPriceProduct(ProductInfo productInfo) {
        if (getSavedLowestPrice(productInfo) > productInfo.getPrice()) {
            return true;
        }
        return false;
    }

    private Integer getSavedLowestPrice(ProductInfo productInfo) {
        return getLowestPriceProductBooks(productInfo.getBrand()).get(productInfo.getCategory()).getPrice();
    }

    private void changeLowestPriceProductInfo(ProductInfo productInfo) {
        getLowestPriceProductBooks(productInfo.getBrand()).put(productInfo.getCategory(), productInfo);
    }

    private boolean hasCategory(String brand, Category category) {
        return getLowestPriceProductBooks(brand)
                .containsKey(category);
    }

    private boolean hasBrand(String brand) {
        return lowestPriceCategories.getBrandCategoryBooks()
                .containsKey(brand);
    }

    private Map<Category, ProductInfo> getLowestPriceProductBooks(String brand) {
        return lowestPriceCategories.getBrandCategoryBooks().get(brand).getCategoryProductBooks();
    }

    private void addNewCategory(ProductInfo productInfo) {
        lowestPriceCategories.getBrandCategoryBooks()
                .get(productInfo.getBrand())
                .getCategoryProductBooks()
                .put(productInfo.getCategory(), productInfo);
    }

    private void addNewBrand(ProductInfo productInfo) {
        lowestPriceCategories.getBrandCategoryBooks()
                .put(productInfo.getBrand(), new CategoryProductTag());
    }
}
