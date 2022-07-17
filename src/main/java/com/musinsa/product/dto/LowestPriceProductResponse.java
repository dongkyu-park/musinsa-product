package com.musinsa.product.dto;

import com.musinsa.product.domain.ProductInfo;
import lombok.Getter;

import java.util.List;

@Getter
public class LowestPriceProductResponse {

    private final List<ProductInfo> lowestPriceProductEachCategories;
    private final int totalPrice;

    public LowestPriceProductResponse(LowestPriceProductDto lowestPriceProductDto) {
        this.lowestPriceProductEachCategories = lowestPriceProductDto.getLowestPriceProductEachCategories();
        this.totalPrice = lowestPriceProductDto.getTotalPrice();
    }
}
