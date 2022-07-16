package com.musinsa.product.repository;

import com.musinsa.product.domain.*;
import com.musinsa.product.dto.ProductInfo;
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
    public ProductInfo findLowestPriceByBrandAndCategory(String brand, Category category) {
        if (BRAND_PRODUCT_STATISTIC.isNotCached(brand, category)) {
            updateCache(brand, category);
        }

        return BRAND_PRODUCT_STATISTIC.getLowestPriceProduct(brand, category);
    }

    private void updateCache(String brand, Category category) {
        ProductInfo productInfo = jpaQueryFactory.select(Projections.fields(ProductInfo.class,
                this.product.price.min().as("price"),
                this.product.id,
                this.product.price,
                this.product.category,
                this.product.brand))
                .from(this.product)
                .where(this.product.brand.eq(brand), this.product.category.eq(category))
                .fetchOne();

        addCache(productInfo);
    }

    private void addCache(ProductInfo productInfo) {
        if (cacheHasBrand(productInfo)) {
            addCacheNewCategory(productInfo);
            return;
        }
        addCacheNewBrand(productInfo);
        addCacheNewCategory(productInfo);
    }

    private boolean cacheHasBrand(ProductInfo productInfo) {
        return BRAND_PRODUCT_STATISTIC.getPriceStatisticByCategoryInBrand().containsKey(productInfo.getBrand());
    }

    private void addCacheNewCategory(ProductInfo productInfo) {
        BRAND_PRODUCT_STATISTIC.getPriceStatisticByCategoryInBrand().get(productInfo.getBrand())
                .getLowestProductInfoInCategory()
                .put(productInfo.getCategory(), productInfo);
    }

    private void addCacheNewBrand(ProductInfo productInfo) {
        BRAND_PRODUCT_STATISTIC.getPriceStatisticByCategoryInBrand().put(productInfo.getBrand(), new CategoryProductStatistic());
    }
}
