package com.musinsa.product.controller;

import com.musinsa.product.domain.Product;
import com.musinsa.product.dto.*;
import com.musinsa.product.service.ProductService;
import com.musinsa.product.valid.CustomValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@RestController
@RequiredArgsConstructor
@Validated
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
        customValidator.validateCategoryAndBrand(lowestPriceProductRequest);
        LowestPriceProductDto lowestPriceProductDto = productService.searchLowestPriceProductByAllCategories(lowestPriceProductRequest);

        return new LowestPriceProductResponse(lowestPriceProductDto);
    }

    @GetMapping("/product/lowest-total-price")
    @ResponseStatus(HttpStatus.OK)
    public LowestTotalPriceProductResponse searchLowestTotalPriceProductByBrand(@RequestParam
                                                                                       @NotBlank(message = "브랜드명이 입력되지 않았거나, 공백이 입력 되었습니다.")
                                                                                       @Size(min = 1, max = 50, message = "브랜드명은 1자 이상, 50자 이하 여야 합니다.") String brand) {
        LowestPriceProductDto lowestPriceProductDto = productService.searchLowestTotalPriceProductByBrand(brand);

        return new LowestTotalPriceProductResponse(brand, lowestPriceProductDto);
    }

    @GetMapping("/product/lowest-and-highest-price")
    @ResponseStatus(HttpStatus.OK)
    public LowestAndHighestPriceProductResponse searchLowestAndHighestPriceProductByCategory (@RequestParam
                                                                                                @NotBlank(message = "카테고리명이 입력되지 않았거나, 공백이 입력 되었습니다.") String category) {
        LowestAndHighestPriceProductDto lowestAndHighestPriceProductDto = productService.searchLowestAndHighestPriceProductByCategory(category);

        return new LowestAndHighestPriceProductResponse(lowestAndHighestPriceProductDto);
    }
}
