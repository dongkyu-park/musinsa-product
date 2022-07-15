package com.musinsa.product.dto;

import com.musinsa.product.domain.ProductType;
import lombok.Getter;

@Getter
public class ProductRequest {

    private ProductType productType;
    private String brand;
    private Integer price;
}
