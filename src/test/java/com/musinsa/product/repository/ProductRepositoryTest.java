package com.musinsa.product.repository;

import com.musinsa.product.config.JpaConfig;
import com.musinsa.product.config.QueryDslConfig;
import com.musinsa.product.domain.Category;
import com.musinsa.product.domain.Product;
import com.musinsa.product.dto.LowestPriceProductRequest;
import com.musinsa.product.dto.ProductInfo;
import com.musinsa.product.dto.ProductPostRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({JpaConfig.class, QueryDslConfig.class})
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("상품 추가. 저장이 성공하면 저장된 엔티티가 반환된다.")
    void save_ok() {
        //given
        String category = "PANTS";
        String brand = "A";
        Integer price = 20000;
        ProductPostRequest stubProductPostRequest = getStubProductPostRequest(category, brand, price);

        //when
        Product savedProduct = productRepository.save(getStubProduct(stubProductPostRequest));

        //then
        assertThat(savedProduct.getCategory().name()).isEqualTo(stubProductPostRequest.getCategory());
        assertThat(savedProduct.getBrand()).isEqualTo(stubProductPostRequest.getBrand());
        assertThat(savedProduct.getPrice()).isEqualTo(stubProductPostRequest.getPrice());
    }

    @Test
    @DisplayName("최저가 조회. 성공하면 조회 정보가 반환된다.")
    void findLowestPriceByBrandAndCategory_ok() {
        //given
        List<String> brands = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H");
        List<String> categories = Arrays.asList("top", "outer", "pants", "sneakers", "bag", "cap", "socks", "accessories");

        //when
        ProductInfo productInfo = productRepository.findLowestPriceByBrandAndCategory(brands.get(1), Category.fromString(categories.get(1)));

        //then
        assertThat(productInfo.getCategory()).isEqualTo(Category.fromString(categories.get(1)));
        assertThat(productInfo.getBrand()).isEqualTo(brands.get(1));
    }

    private ProductPostRequest getStubProductPostRequest(String category, String brand, Integer price) {
        return new ProductPostRequest(category, brand, price);
    }

    private Product getStubProduct(ProductPostRequest stubProductPostRequest) {
        return new Product(stubProductPostRequest);
    }
}
