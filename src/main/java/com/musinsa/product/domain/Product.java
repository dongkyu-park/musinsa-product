package com.musinsa.product.domain;

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
    private ProductType type;

    private String brand;

    @Column(nullable = false)
    private Integer price;

    private boolean isDeleted = false;

    public Product(ProductType productType, String brand, Integer price) {
        this.type = productType;
        this.brand = brand;
        this.price = price;
    }
}
