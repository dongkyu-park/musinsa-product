package com.musinsa.product.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Builder
public class lowestPriceProductRequest {

    private final List<@NotBlank(message = "카테고리명이 입력되지 않았거나, 공백이 입력 되었습니다.")
            String> category;
    private final List<@NotBlank(message = "브랜드명이 입력되지 않았거나, 공백이 입력 되었습니다.")
    @Size(min = 1, max = 50, message = "브랜드명은 1자 이상, 50자 이하 여야 합니다.")
            String> brand;
}
