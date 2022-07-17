package com.musinsa.product.dto;

import lombok.Getter;

@Getter
public class LowestAndHighestPriceProductResponse {

    private final String lowestPriceBrand;
    private final int lowestPrice;
    private final String highestPriceBrand;
    private final int highestPrice;

    public LowestAndHighestPriceProductResponse(LowestAndHighestPriceProductDto lowestAndHighestPriceProductDto) {
        this.lowestPriceBrand = lowestAndHighestPriceProductDto.getLowestPriceProduct().getBrand();
        this.lowestPrice = lowestAndHighestPriceProductDto.getLowestPriceProduct().getPrice();
        this.highestPriceBrand = lowestAndHighestPriceProductDto.getHighestPriceProduct().getBrand();
        this.highestPrice = lowestAndHighestPriceProductDto.getHighestPriceProduct().getPrice();
    }
}
