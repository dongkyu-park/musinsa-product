package com.musinsa.product.dto;

import lombok.*;

import java.util.List;

@Getter
@Builder
public class SearchLowestPriceProductRequest {

    private final List<String> category;
    private final List<String> brand;
}
