package com.musinsa.product.controller;

import com.musinsa.product.domain.Category;
import com.musinsa.product.domain.Product;
import com.musinsa.product.dto.SearchLowestPriceProductRequest;
import com.musinsa.product.dto.SearchLowestPriceProductResponse;
import com.musinsa.product.dto.ProductPostRequest;
import com.musinsa.product.dto.ProductResponse;
import com.musinsa.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/product")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse<Product> addProduct(@RequestBody @Validated ProductPostRequest productPostRequest) {
        return new ProductResponse<>("상품이 정상적으로 추가 되었습니다.", productService.addProduct(productPostRequest));
    }

    @GetMapping("/product/lowest-price")
    @ResponseStatus(HttpStatus.OK)
    public SearchLowestPriceProductResponse searchLowestPriceProductByAllCategories(@ModelAttribute @Validated SearchLowestPriceProductRequest searchLowestPriceProductRequest) {
        SearchLowestPriceProductResponse searchLowestPriceProductResponse = new SearchLowestPriceProductResponse();
        int requestParamCount = searchLowestPriceProductRequest.getBrand().size();

        for (int i = 0; i < requestParamCount; i++) {
            String brand = searchLowestPriceProductRequest.getBrand().get(i);
            Category category = Category.fromString(searchLowestPriceProductRequest.getCategory().get(i));

            searchLowestPriceProductResponse
                    .addLowestPriceProductInCategory(productService.searchLowestPriceProductByAllCategories(brand, category));
        }

        return searchLowestPriceProductResponse;
    }
}
