package com.musinsa.product.repository;

import com.musinsa.product.domain.*;
import com.musinsa.product.domain.ProductInfo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductQuerydslRepositoryImpl implements ProductQuerydslRepository {

    public static final BrandProductStatistic BRAND_PRODUCT_STATISTIC = new BrandProductStatistic();
    public static final CategoryProductStatistic CATEGORY_PRODUCT_STATISTIC = new CategoryProductStatistic();

    private final JPAQueryFactory jpaQueryFactory;
    private final QProduct product = QProduct.product;

    @Override
    public ProductInfo findLowestPriceByBrandAndCategory(String brand, Category category) {
        if (BRAND_PRODUCT_STATISTIC.isNotCached(brand, category)) {
            updateBrandCache(brand, category);
        }

        return BRAND_PRODUCT_STATISTIC.getLowestPriceProductInfo(brand, category);
    }

    @Override
    public List<ProductInfo> findLowestAndHighestPriceByCategory(Category category) {
        if (CATEGORY_PRODUCT_STATISTIC.isNotCached(category)) {
            updateCategoryCache(category);
        }

        return CATEGORY_PRODUCT_STATISTIC.getLowestAndHighestPriceProductInfoByCategory(category);
    }

    private void updateCategoryCache(Category category) {
        QProduct subProduct = new QProduct("subProduct");

        ProductInfo lowestPriceProductInfo = jpaQueryFactory.select(Projections.fields(ProductInfo.class,
                this.product.id,
                this.product.price,
                this.product.category,
                this.product.brand))
                .from(this.product)
                .where(this.product.price.eq(
                        jpaQueryFactory.select(subProduct.price.min())
                                .from(subProduct)
                                .where(subProduct.category.eq(category))))
                .limit(1)
                .fetchOne();

        ProductInfo highestPriceProductInfo = jpaQueryFactory.select(Projections.fields(ProductInfo.class,
                this.product.id,
                this.product.price,
                this.product.category,
                this.product.brand))
                .from(this.product)
                .where(this.product.price.eq(
                        jpaQueryFactory.select(subProduct.price.max())
                                .from(subProduct)
                                .where(subProduct.category.eq(category))))
                .limit(1)
                .fetchOne();

        CATEGORY_PRODUCT_STATISTIC.addCache(lowestPriceProductInfo, highestPriceProductInfo, category);
    }

    private void updateBrandCache(String brand, Category category) {
        ProductInfo productInfo = jpaQueryFactory.select(Projections.fields(ProductInfo.class,
                this.product.price.min().as("price"),
                this.product.id,
                this.product.price,
                this.product.category,
                this.product.brand))
                .from(this.product)
                .where(this.product.brand.eq(brand), this.product.category.eq(category))
                .fetchOne();

        BRAND_PRODUCT_STATISTIC.addCache(productInfo);
    }
}
