package com.musinsa.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    @NotBlank(message = "카테고리명이 입력되지 않았거나, 공백이 입력 되었습니다.")
    private String category;

    @NotBlank(message = "브랜드명이 입력되지 않았거나, 공백이 입력 되었습니다.")
    @Size(min = 1, max = 50, message = "브랜드명은 1자 이상, 50자 이하 여야 합니다.")
    private String brand;

    @NotNull(message = "상품 가격이 입력되지 않았습니다.")
    @PositiveOrZero(message = "상품 가격은 음수값이 입력될 수 없습니다.")
    private Integer price;
}
