package com.musinsa.product.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductPostResponse<T> {

    private String message;
    private T data;
}
