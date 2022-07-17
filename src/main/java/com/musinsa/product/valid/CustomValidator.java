package com.musinsa.product.valid;

import com.musinsa.product.domain.Category;
import com.musinsa.product.dto.requestdto.LowestPriceProductRequest;
import com.musinsa.product.exception.CustomException;
import com.musinsa.product.exception.ErrorCode;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomValidator {

    public void validateCategoryAndBrand(LowestPriceProductRequest lowestPriceProductRequest) {
        if (missingParams(lowestPriceProductRequest)) {
            throw new CustomException(ErrorCode.MISSING_PARAM);
        }

        if (doNotMatchCountOfBrandAndCategory(lowestPriceProductRequest)) {
            throw new CustomException(ErrorCode.NOT_MATCH_COUNT_BRAND_WITH_CATEGORY);
        }

        if (invalidCategories(lowestPriceProductRequest)) {
            throw new CustomException(ErrorCode.INVALID_CATEGORY_NAME);
        }
    }

    private boolean missingParams(LowestPriceProductRequest lowestPriceProductRequest) {
        return lowestPriceProductRequest.getCategory() == null || lowestPriceProductRequest.getBrand() == null;
    }

    public void validateCategory(Category category) {
        if (invalidCategory(category)) {
            throw new CustomException(ErrorCode.INVALID_CATEGORY_NAME);
        }
    }

    private boolean doNotMatchCountOfBrandAndCategory(LowestPriceProductRequest lowestPriceProductRequest) {
        return lowestPriceProductRequest.getBrand().size() != lowestPriceProductRequest.getCategory().size();
    }

    private boolean invalidCategories(LowestPriceProductRequest lowestPriceProductRequest) {
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

    private boolean invalidCategory(Category category) {
        return !Arrays.stream(Category.values())
                .anyMatch(savedCategory -> savedCategory.equals(category));
    }
}
