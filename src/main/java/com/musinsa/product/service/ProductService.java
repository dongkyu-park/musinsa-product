package com.musinsa.product.service;

import com.musinsa.product.domain.BrandProductStatistic;
import com.musinsa.product.domain.Category;
import com.musinsa.product.domain.CategoryProductStatistic;
import com.musinsa.product.domain.Product;
import com.musinsa.product.dto.LowestAndHighestPriceProductDto;
import com.musinsa.product.dto.LowestPriceProductDto;
import com.musinsa.product.dto.requestdto.LowestPriceProductRequest;
import com.musinsa.product.dto.requestdto.ProductPostRequest;
import com.musinsa.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    public static final int COUNT_ALL_CATEGORY = Category.values().length;
    public static final BrandProductStatistic BRAND_PRODUCT_STATISTIC = new BrandProductStatistic();
    public static final CategoryProductStatistic CATEGORY_PRODUCT_STATISTIC = new CategoryProductStatistic();

    private final ProductRepository productRepository;

    public Product addProduct(ProductPostRequest productPostRequest) {
        Product product = new Product(productPostRequest);

        return productRepository.save(product);
    }

    public LowestPriceProductDto searchLowestPriceProductByAllCategories(LowestPriceProductRequest lowestPriceProductRequest) {
        System.out.println("test");
        LowestPriceProductDto lowestPriceProductDto = new LowestPriceProductDto();
        int requestParamCount = lowestPriceProductRequest.getBrand().size();

        for (int i = 0; i < requestParamCount; i++) {
            String brand = lowestPriceProductRequest.getBrand().get(i);
            Category category = Category.fromString(lowestPriceProductRequest.getCategory().get(i));

            if (BRAND_PRODUCT_STATISTIC.isNotCached(brand, category)) {
                updateBrandCache(brand, category);
            }

            lowestPriceProductDto
                    .addLowestPriceProductInCategory(BRAND_PRODUCT_STATISTIC.getLowestPriceProductInfo(brand, category));
        }

        return lowestPriceProductDto;
    }

    public LowestPriceProductDto searchLowestTotalPriceProductByBrand(String brand) {
        LowestPriceProductDto lowestPriceProductDto = new LowestPriceProductDto();
        Category[] categories = Category.values();

        for (int i = 0; i < COUNT_ALL_CATEGORY; i++) {
            Category category = categories[i];

            if (BRAND_PRODUCT_STATISTIC.isNotCached(brand, category)) {
                updateBrandCache(brand, category);
            }

            lowestPriceProductDto
                    .addLowestPriceProductInCategory(BRAND_PRODUCT_STATISTIC.getLowestPriceProductInfo(brand, category));
        }

        return lowestPriceProductDto;
    }

    public LowestAndHighestPriceProductDto searchLowestAndHighestPriceProductByCategory(String requestCategory) {
        Category category = Category.fromString(requestCategory);

        if (CATEGORY_PRODUCT_STATISTIC.isNotCached(category)) {
            updateCategoryCache(category);
        }

        return new LowestAndHighestPriceProductDto(CATEGORY_PRODUCT_STATISTIC.getLowestAndHighestPriceProductInfoByCategory(category));
    }

    private void updateBrandCache(String brand, Category category) {
        BRAND_PRODUCT_STATISTIC.addCache(productRepository.findLowestPriceByBrandAndCategory(brand, category));
    }

    private void updateCategoryCache(Category category) {
        CATEGORY_PRODUCT_STATISTIC.addCache(productRepository.findLowestAndHighestPriceByCategory(category));
    }
}
