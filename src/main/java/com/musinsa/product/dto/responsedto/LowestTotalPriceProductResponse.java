package com.musinsa.product.dto.responsedto;

import com.musinsa.product.dto.LowestPriceProductDto;
import lombok.Getter;

@Getter
public class LowestTotalPriceProductResponse {

    private final String brand;
    private final int totalPrice;

    public LowestTotalPriceProductResponse(String brand, LowestPriceProductDto lowestPriceProductDto) {
        this.brand = brand;
        this.totalPrice = lowestPriceProductDto.getTotalPrice();
    }
}
