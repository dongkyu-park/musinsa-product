package com.musinsa.product.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class LowestPriceProductDto {

    private final List<ProductInfo> lowestPriceProductEachCategories = new ArrayList<>();
    private int totalPrice;

    public void addLowestPriceProductInCategory(ProductInfo productInfo) {
        lowestPriceProductEachCategories.add(productInfo);
        totalPrice += productInfo.getPrice();
    }
}
