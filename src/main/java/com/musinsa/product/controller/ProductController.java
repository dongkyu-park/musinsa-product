package com.musinsa.product.controller;

import com.musinsa.product.domain.Product;
import com.musinsa.product.dto.*;
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
    public LowestPriceProductResponse searchLowestPriceProductByAllCategories(@ModelAttribute @Validated LowestPriceProductRequest lowestPriceProductRequest) {
        customValidator.validateLowestPriceProductRequest(lowestPriceProductRequest);
        LowestPriceProductDto lowestPriceProductDto = productService.searchLowestPriceProductByAllCategories(lowestPriceProductRequest);

        return new LowestPriceProductResponse(lowestPriceProductDto);
    }
}
