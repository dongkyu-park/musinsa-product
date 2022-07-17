package com.musinsa.product.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductInfo {

    private Long id;
    private Category category;
    private String brand;
    private Integer price;
}
