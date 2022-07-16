package com.musinsa.product.repository;

import com.musinsa.product.domain.*;
import com.musinsa.product.dto.ProductDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductQuerydslRepositoryImpl implements ProductQuerydslRepository {

    private static final BrandCategoryStatistic BRAND_PRODUCT_STATISTIC = new BrandCategoryStatistic();

    private final JPAQueryFactory jpaQueryFactory;
    private final QProduct product = QProduct.product;

    @Override
    public ProductDto findLowestPriceByBrandAndCategory(String brand, Category category) {
        if (BRAND_PRODUCT_STATISTIC.isNotCached(brand, category)) {
            updateCache(brand, category);
        }

        return BRAND_PRODUCT_STATISTIC.getLowestPriceByBrandAndCategory(brand, category);
    }

    private void updateCache(String brand, Category category) {
        ProductDto productDto = jpaQueryFactory.select(Projections.fields(ProductDto.class,
                this.product.price.min().as("price"),
                this.product.id,
                this.product.price,
                this.product.category,
                this.product.brand))
                .from(this.product)
                .where(this.product.brand.eq(brand), this.product.category.eq(category))
                .fetchOne();

        addCache(productDto);
    }

    private void addCache(ProductDto productDto) {
        if (cacheHasBrand(productDto)) {
            addCacheNewCategory(productDto);
            return;
        }
        addCacheNewBrand(productDto);
        addCacheNewCategory(productDto);
    }

    private boolean cacheHasBrand(ProductDto productDto) {
        return BRAND_PRODUCT_STATISTIC.getPriceStatisticByCategoryInBrand().containsKey(productDto.getBrand());
    }

    private void addCacheNewCategory(ProductDto productDto) {
        BRAND_PRODUCT_STATISTIC.getPriceStatisticByCategoryInBrand().get(productDto.getBrand())
                .getLowestProductInfoInCategory()
                .put(productDto.getCategory(), productDto);
    }

    private void addCacheNewBrand(ProductDto productDto) {
        BRAND_PRODUCT_STATISTIC.getPriceStatisticByCategoryInBrand().put(productDto.getBrand(), new CategoryProductStatistic());
    }
}
