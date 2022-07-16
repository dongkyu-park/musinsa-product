package com.musinsa.product.domain;

import com.musinsa.product.dto.ProductPostRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String brand;

    @Column(nullable = false)
    private Integer price;

    private boolean isDeleted = false;

    public Product(ProductPostRequest productPostRequest) {
        this.category = Category.fromString(productPostRequest.getCategory());
        this.brand = productPostRequest.getBrand();
        this.price = productPostRequest.getPrice();
    }
}
