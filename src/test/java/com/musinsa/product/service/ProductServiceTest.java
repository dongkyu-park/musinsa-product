package com.musinsa.product.service;

import com.musinsa.product.domain.Product;
import com.musinsa.product.dto.LowestPriceProductDto;
import com.musinsa.product.dto.requestdto.LowestPriceProductRequest;
import com.musinsa.product.dto.requestdto.ProductPostRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    @DisplayName("상품 추가. 저장이 성공하면 저장된 엔티티가 반환된다.")
    void addProduct_ok() {
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

    @Test
    @DisplayName("최저가 조회. 성공하면 조회 정보가 반환된다.")
    void searchLowestPriceProductByAllCategories_ok() {
        //given
        List<String> brands = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H");
        List<String> categories = Arrays.asList("top", "outer", "pants", "sneakers", "bag", "cap", "socks", "accessories");
        LowestPriceProductRequest stubLowestPriceProductRequest = getStubLowestPriceProductRequest(brands, categories);

        //when
        LowestPriceProductDto lowestPriceProductDto = productService.searchLowestPriceProductByAllCategories(stubLowestPriceProductRequest);

        //then
        assertThat(lowestPriceProductDto.getLowestPriceProductEachCategories().size()).isNotEqualTo(0);
    }

    @Test
    @DisplayName("한 브랜드 최저가 조회. 성공하면 조회 정보가 반환된다.")
    void searchLowestTotalPriceProductByBrand_ok() {
        //given
        String brand = "A";

        //when
        LowestPriceProductDto lowestPriceProductDto = productService.searchLowestTotalPriceProductByBrand(brand);

        //then
        assertThat(lowestPriceProductDto.getLowestPriceProductEachCategories().size()).isNotEqualTo(0);
    }

    private ProductPostRequest getStubProductPostRequest(String category, String brand, Integer price) {
        return new ProductPostRequest(category, brand, price);
    }

    private LowestPriceProductRequest getStubLowestPriceProductRequest(List<String> brands, List<String> categories) {
        return LowestPriceProductRequest.builder()
                .brand(brands)
                .category(categories)
                .build();
    }
}
