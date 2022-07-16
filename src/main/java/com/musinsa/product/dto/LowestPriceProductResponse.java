package com.musinsa.product.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class LowestPriceProductResponse {

    private final List<ProductDto> lowestPriceProductEachCategories;
    private int totalPrice;

    public LowestPriceProductResponse(LowestPriceProductDto lowestPriceProductDto) {
        this.lowestPriceProductEachCategories = lowestPriceProductDto.getLowestPriceProductEachCategories();
        this.totalPrice = lowestPriceProductDto.getTotalPrice();
    }
}
