package com.musinsa.product.dto;

import com.musinsa.product.domain.ProductInfo;
import lombok.Getter;

@Getter
public class LowestAndHighestPriceProductDto {

    private final ProductInfo lowestPriceProduct;
    private final ProductInfo highestPriceProduct;

    public LowestAndHighestPriceProductDto(ProductInfo lowestPriceProductInfo, ProductInfo highestPriceProductInfo) {
        this.lowestPriceProduct = lowestPriceProductInfo;
        this.highestPriceProduct = highestPriceProductInfo;
    }
}
