package com.musinsa.product.repository;

import com.musinsa.product.domain.*;
import com.musinsa.product.domain.ProductInfo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductQuerydslRepositoryImpl implements ProductQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final QProduct product = QProduct.product;

    @Override
    public ProductInfo findLowestPriceByBrandAndCategory(String brand, Category category) {
        return jpaQueryFactory.select(Projections.fields(ProductInfo.class,
                this.product.price.min().as("price"),
                this.product.id,
                this.product.price,
                this.product.category,
                this.product.brand))
                .from(this.product)
                .where(this.product.brand.eq(brand), this.product.category.eq(category))
                .fetchOne();
    }

    @Override
    public List<ProductInfo> findLowestAndHighestPriceByCategory(Category category) {
        QProduct subProduct = new QProduct("subProduct");
        List<ProductInfo> lowestAndHighestPriceProductInfo = new ArrayList<>();

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

        lowestAndHighestPriceProductInfo.add(lowestPriceProductInfo);
        lowestAndHighestPriceProductInfo.add(highestPriceProductInfo);

        return lowestAndHighestPriceProductInfo;
    }
}
