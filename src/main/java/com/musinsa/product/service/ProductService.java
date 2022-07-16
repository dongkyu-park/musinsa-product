package com.musinsa.product.service;

import com.musinsa.product.domain.Category;
import com.musinsa.product.domain.Product;
import com.musinsa.product.dto.*;
import com.musinsa.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    public static int COUNT_ALL_CATEGORY = Category.values().length;

    private final ProductRepository productRepository;

    public Product addProduct(ProductPostRequest productPostRequest) {
        Product product = new Product(productPostRequest);

        return productRepository.save(product);
    }

    public LowestPriceProductDto searchLowestPriceProductByAllCategories(LowestPriceProductRequest lowestPriceProductRequest) {
        LowestPriceProductDto lowestPriceProductDto = new LowestPriceProductDto();
        int requestParamCount = lowestPriceProductRequest.getBrand().size();

        for (int i = 0; i < requestParamCount; i++) {
            String brand = lowestPriceProductRequest.getBrand().get(i);
            Category category = Category.fromString(lowestPriceProductRequest.getCategory().get(i));

            lowestPriceProductDto
                    .addLowestPriceProductInCategory(productRepository.findLowestPriceByBrandAndCategory(brand, category));
        }

        return lowestPriceProductDto;
    }

    public LowestPriceProductDto searchLowestTotalPriceProductByOneBrand(String brand) {
        LowestPriceProductDto lowestPriceProductDto = new LowestPriceProductDto();
        Category[] categories = Category.values();

        for (int i = 0; i < COUNT_ALL_CATEGORY; i++) {
            Category category = categories[i];

            lowestPriceProductDto
                    .addLowestPriceProductInCategory(productRepository.findLowestPriceByBrandAndCategory(brand, category));
        }

        return lowestPriceProductDto;
    }
}
