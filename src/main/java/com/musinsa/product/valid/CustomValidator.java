package com.musinsa.product.valid;

import com.musinsa.product.domain.Category;
import com.musinsa.product.dto.SearchLowestPriceProductRequest;
import com.musinsa.product.exception.CustomException;
import com.musinsa.product.exception.ErrorCode;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomValidator {

    public void validateSearchLowestPriceProductRequest(SearchLowestPriceProductRequest searchLowestPriceProductRequest) {
        if (doNotMatchCountOfBrandAndCategory(searchLowestPriceProductRequest)) {
            throw new CustomException(ErrorCode.NOT_MATCH_COUNT_BRAND_WITH_CATEGORY);
        }

        if (invalidCategories(searchLowestPriceProductRequest)) {
            throw new CustomException(ErrorCode.INVALID_CATEGORY_NAME);
        }
    }

    private boolean doNotMatchCountOfBrandAndCategory(SearchLowestPriceProductRequest searchLowestPriceProductRequest) {
        return searchLowestPriceProductRequest.getBrand().size() != searchLowestPriceProductRequest.getCategory().size();
    }

    private boolean invalidCategories(SearchLowestPriceProductRequest searchLowestPriceProductRequest) {
        List<String> requestCategories = searchLowestPriceProductRequest.getCategory().stream()
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
