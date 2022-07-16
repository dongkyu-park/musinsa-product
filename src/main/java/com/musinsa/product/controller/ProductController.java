package com.musinsa.product.controller;

import com.musinsa.product.domain.Category;
import com.musinsa.product.domain.Product;
import com.musinsa.product.dto.lowestPriceProductRequest;
import com.musinsa.product.dto.lowestPriceProductResponse;
import com.musinsa.product.dto.ProductPostRequest;
import com.musinsa.product.dto.ProductResponse;
import com.musinsa.product.service.ProductService;
import com.musinsa.product.valid.CustomValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CustomValidator customValidator;

    @PostMapping("/product")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse<Product> addProduct(@RequestBody @Validated ProductPostRequest productPostRequest) {
        return new ProductResponse<>("상품이 정상적으로 추가 되었습니다.", productService.addProduct(productPostRequest));
    }

    @GetMapping("/product/lowest-price")
    @ResponseStatus(HttpStatus.OK)
    public lowestPriceProductResponse categoryLowestPriceProductByAllCategories(@ModelAttribute @Validated lowestPriceProductRequest lowestPriceProductRequest) {
        customValidator.validateLowestPriceProductRequest(lowestPriceProductRequest);

        lowestPriceProductResponse lowestPriceProductResponse = new lowestPriceProductResponse();
        int requestParamCount = lowestPriceProductRequest.getBrand().size();

        for (int i = 0; i < requestParamCount; i++) {
            String brand = lowestPriceProductRequest.getBrand().get(i);
            Category category = Category.fromString(lowestPriceProductRequest.getCategory().get(i));

            lowestPriceProductResponse
                    .addLowestPriceProductInCategory(productService.searchLowestPriceProductByAllCategories(brand, category));
        }

        return lowestPriceProductResponse;
    }
}
