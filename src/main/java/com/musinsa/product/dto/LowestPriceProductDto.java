package com.musinsa.product.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class LowestPriceProductDto {

    private final List<ProductDto> lowestPriceProductEachCategories = new ArrayList<>();
    private int totalPrice;

    public void addLowestPriceProductInCategory(ProductDto productDto) {
        lowestPriceProductEachCategories.add(productDto);
        totalPrice += productDto.getPrice();
    }
}
