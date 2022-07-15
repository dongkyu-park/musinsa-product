package com.musinsa.product.domain;

import com.musinsa.product.exception.CustomException;
import com.musinsa.product.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum ProductType {

    TOP("상의"),
    OUTER("아우터"),
    PANTS("바지"),
    SNEAKERS("스니커즈"),
    BAG("가방"),
    CAP("모자"),
    SOCKS("양말"),
    ACCESSORIES("액세서리"),
    ;

    private final String title;

    public static ProductType fromName(String requestName) {
        return Arrays.stream(values())
                .filter(productType -> productType.name().equals(requestName.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_CATEGORY_NAME));
    }
}
