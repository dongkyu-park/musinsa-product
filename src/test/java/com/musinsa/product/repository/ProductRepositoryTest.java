package com.musinsa.product.repository;

import com.musinsa.product.config.JpaConfig;
import com.musinsa.product.domain.Product;
import com.musinsa.product.dto.ProductRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaConfig.class)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("저장이 성공하면 저장된 엔티티가 반환된다.")
    void ok() {
        //given
        String category = "PANTS";
        String brand = "A";
        Integer price = 20000;
        ProductRequest stubProductRequest = getStubProductRequest(category, brand, price);

        //when
        Product savedProduct = productRepository.save(getStubProduct(stubProductRequest));

        //then
        assertThat(savedProduct.getCategory().name()).isEqualTo(stubProductRequest.getCategory());
        assertThat(savedProduct.getBrand()).isEqualTo(stubProductRequest.getBrand());
        assertThat(savedProduct.getPrice()).isEqualTo(stubProductRequest.getPrice());
    }

    private ProductRequest getStubProductRequest(String category, String brand, Integer price) {
        return new ProductRequest(category, brand, price);
    }

    private Product getStubProduct(ProductRequest stubProductRequest) {
        return new Product(stubProductRequest);
    }
}
