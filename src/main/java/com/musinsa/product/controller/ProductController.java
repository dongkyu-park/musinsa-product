package com.musinsa.product.controller;

import com.musinsa.product.domain.Product;
import com.musinsa.product.dto.ProductPostRequest;
import com.musinsa.product.dto.ProductResponse;
import com.musinsa.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/product")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse<Product> addProduct(@RequestBody @Validated ProductPostRequest productPostRequest) {
        return new ProductResponse<>("상품이 정상적으로 추가 되었습니다.", productService.addProduct(productPostRequest));
    }
}
