package com.musinsa.product.valid;

import com.musinsa.product.domain.Category;
import com.musinsa.product.dto.lowestPriceProductRequest;
import com.musinsa.product.exception.CustomException;
import com.musinsa.product.exception.ErrorCode;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomValidator {

    public void validateLowestPriceProductRequest(lowestPriceProductRequest lowestPriceProductRequest) {
        if (doNotMatchCountOfBrandAndCategory(lowestPriceProductRequest)) {
            throw new CustomException(ErrorCode.NOT_MATCH_COUNT_BRAND_WITH_CATEGORY);
        }

        if (invalidCategories(lowestPriceProductRequest)) {
            throw new CustomException(ErrorCode.INVALID_CATEGORY_NAME);
        }
    }

    private boolean doNotMatchCountOfBrandAndCategory(lowestPriceProductRequest lowestPriceProductRequest) {
        return lowestPriceProductRequest.getBrand().size() != lowestPriceProductRequest.getCategory().size();
    }

    private boolean invalidCategories(lowestPriceProductRequest lowestPriceProductRequest) {
        List<String> requestCategories = lowestPriceProductRequest.getCategory().stream()
                .map(category -> category.toUpperCase())
                .sorted()
                .collect(Collectors.toList());

        List<String> savedCategories = Arrays.stream(Category.values())
                .map(category -> category.name())
                .sorted()
                .collect(Collectors.toList());

        return !requestCategories.equals(savedCategories);
    }
}
