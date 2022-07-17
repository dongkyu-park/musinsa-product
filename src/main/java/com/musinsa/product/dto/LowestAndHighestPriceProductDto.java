package com.musinsa.product.dto;

import com.musinsa.product.domain.ProductInfo;
import lombok.Getter;

import java.util.List;

@Getter
public class LowestAndHighestPriceProductDto {

    private final ProductInfo lowestPriceProduct;
    private final ProductInfo highestPriceProduct;

    public LowestAndHighestPriceProductDto(List<ProductInfo> lowestAndHighestPriceInfo) {
        this.lowestPriceProduct = lowestAndHighestPriceInfo.get(0);
        this.highestPriceProduct = lowestAndHighestPriceInfo.get(1);
    }
}
