package com.musinsa.product.repository;

import com.musinsa.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductQuerydslRepository {
}
