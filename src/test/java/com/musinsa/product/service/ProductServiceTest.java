package com.musinsa.product.service;

import com.musinsa.product.domain.Product;
import com.musinsa.product.dto.ProductPostRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    @DisplayName("저장이 성공하면 저장된 엔티티가 반환된다.")
    void ok() {
        //given
        String category = "PANTS";
        String brand = "A";
        Integer price = 20000;
        ProductPostRequest stubProductPostRequest = getStubProductPostRequest(category, brand, price);

        //when
        Product savedProduct = productService.addProduct(stubProductPostRequest);

        //then
        assertThat(savedProduct.getCategory().name()).isEqualTo(category);
        assertThat(savedProduct.getBrand()).isEqualTo(brand);
        assertThat(savedProduct.getPrice()).isEqualTo(price);
    }

    private ProductPostRequest getStubProductPostRequest(String category, String brand, Integer price) {
        return new ProductPostRequest(category, brand, price);
    }
}
