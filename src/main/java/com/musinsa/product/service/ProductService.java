package com.musinsa.product.service;

import com.musinsa.product.domain.Product;
import com.musinsa.product.dto.ProductPostRequest;
import com.musinsa.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product addProduct(ProductPostRequest productPostRequest) {
        Product product = new Product(productPostRequest);

        return productRepository.save(product);
    }
}
