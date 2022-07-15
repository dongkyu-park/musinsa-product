package com.musinsa.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductResponse<T> {

    private String message;
    private T data;
}
