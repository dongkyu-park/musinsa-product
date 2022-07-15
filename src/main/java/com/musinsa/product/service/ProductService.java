package com.musinsa.product.service;

import com.musinsa.product.domain.Product;
import com.musinsa.product.dto.ProductRequest;
import com.musinsa.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product addProduct(ProductRequest productRequest) {
        Product product = new Product(productRequest);

        return productRepository.save(product);
    }
}
