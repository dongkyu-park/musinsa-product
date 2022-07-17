package com.musinsa.product.controller;

import com.musinsa.product.domain.Category;
import com.musinsa.product.domain.Product;
import com.musinsa.product.dto.LowestPriceProductDto;
import com.musinsa.product.dto.LowestPriceProductRequest;
import com.musinsa.product.domain.ProductInfo;
import com.musinsa.product.dto.ProductPostRequest;
import com.musinsa.product.service.ProductService;
import com.musinsa.product.valid.CustomValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({ProductController.class, CustomValidator.class})
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    @DisplayName("상품 추가. 잘못 된 파라미터값으로 요청이 올 경우, 요청이 실패하고 400 에러 코드 리턴")
    void addProduct_fail() throws Exception {
        //given
        String zeroString = "";
        String blank = " ";

        String zeroStringBody = "{\n" +
                "\"category\": \"" + zeroString + "\",\n" +
                "\"brand\": \"" + zeroString + "\",\n" +
                "\"price\": " + zeroString + "\n" +
                "}";
        String blankStringBody = "{\n" +
                "\"category\": \"" + blank + "\",\n" +
                "\"brand\": \"" + blank + "\",\n" +
                "\"price\": " + blank + "\n" +
                "}";
        String overStringBody = "{\n" +
                "\"category\": \"" + "PANTS" + "\",\n" +
                "\"brand\": \"" + "abcdefghijabcdefghijabcdefghijabcdefghijabcdefghij1" + "\",\n" +
                "\"price\": " + "10000" + "\n" +
                "}";
        String negativePriceBody = "{\n" +
                "\"category\": \"" + "PANTS" + "\",\n" +
                "\"brand\": \"" + "A" + "\",\n" +
                "\"price\": " + "-10000" + "\n" +
                "}";

        //when
        //카테고리명 누락
        ResultActions result1 = mockMvc.perform(MockMvcRequestBuilders
                .post("/product")
                .content(zeroStringBody)
                .contentType(MediaType.APPLICATION_JSON));
        //카테고리명 공백
        ResultActions result2 = mockMvc.perform(MockMvcRequestBuilders
                .post("/product")
                .content(blankStringBody)
                .contentType(MediaType.APPLICATION_JSON));

        //브랜드명 누락
        ResultActions result3 = mockMvc.perform(MockMvcRequestBuilders
                .post("/product")
                .content(zeroStringBody)
                .contentType(MediaType.APPLICATION_JSON));
        //브랜드명 공백
        ResultActions result4 = mockMvc.perform(MockMvcRequestBuilders
                .post("/product")
                .content(blankStringBody)
                .contentType(MediaType.APPLICATION_JSON));
        //브랜드명 50글자 초과
        ResultActions result5 = mockMvc.perform(MockMvcRequestBuilders
                .post("/product")
                .content(overStringBody)
                .contentType(MediaType.APPLICATION_JSON));

        //가격 누락
        ResultActions result6 = mockMvc.perform(MockMvcRequestBuilders
                .post("/product")
                .content(zeroStringBody)
                .contentType(MediaType.APPLICATION_JSON));
        //가격 공백
        ResultActions result7 = mockMvc.perform(MockMvcRequestBuilders
                .post("/product")
                .content(blankStringBody)
                .contentType(MediaType.APPLICATION_JSON));
        //가격 음수
        ResultActions result8 = mockMvc.perform(MockMvcRequestBuilders
                .post("/product")
                .content(negativePriceBody)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        //카테고리명 누락
        result1.andExpect(status().isBadRequest());
        //카테고리명 공백
        result2.andExpect(status().isBadRequest());

        //브랜드명 누락
        result3.andExpect(status().isBadRequest());
        //브랜드명 공백
        result4.andExpect(status().isBadRequest());
        //브랜드명 50글자 초과
        result5.andExpect(status().isBadRequest());

        //가격 누락
        result6.andExpect(status().isBadRequest());
        //가격 공백
        result7.andExpect(status().isBadRequest());
        //가격 음수
        result8.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("상품 추가. 요청 성공")
    void addProduct_ok() throws Exception {
        //given
        String category = "PANTS";
        String brand = "A";
        Integer price = 20000;
        ProductPostRequest stubProductPostRequest = getStubProductPostRequest(category, brand, price);
        Product stubProduct = getStubProduct(stubProductPostRequest);
        String body = "{\n" +
                "\"category\": \"" + category + "\",\n" +
                "\"brand\": \"" + brand + "\",\n" +
                "\"price\": " + price + "\n" +
                "}";

        Mockito.when(productService.addProduct(any(ProductPostRequest.class)))
                .thenReturn(stubProduct);

        //when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .post("/product")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.category", is(category)))
                .andExpect(jsonPath("$.data.brand", is(brand)))
                .andExpect(jsonPath("$.data.price", is(price)));
    }

    @Test
    @DisplayName("최저가 조회. 잘못 된 파라미터값으로 요청이 올 경우, 요청이 실패하고 400 에러 코드 리턴")
    void searchLowestPriceProductByAllCategories_fail() throws Exception {
        //given

        //when
        //카테고리명 누락
        ResultActions result1 = mockMvc.perform(MockMvcRequestBuilders
                .get("/product/lowest-price")
                .param("category", "top, outer, pants, sneakers, bag,, socks, accessories")
                .param("brand", "A, B, C, D, E, F, G, H")
                .contentType(MediaType.APPLICATION_JSON));
        //카테고리명 공백
        ResultActions result2 = mockMvc.perform(MockMvcRequestBuilders
                .get("/product/lowest-price")
                .param("category", "top, outer, pants, sneakers, bag, cap, , accessories")
                .param("brand", "A, B, C, D, E, F, G, H")
                .contentType(MediaType.APPLICATION_JSON));
        //유효하지 않은 카테고리명
        ResultActions result3 = mockMvc.perform(MockMvcRequestBuilders
                .get("/product/lowest-price")
                .param("category", "top, shoes, pants, sneaker, bag, cap, , accessories")
                .param("brand", "A, B, C, D, E, F, G, H")
                .contentType(MediaType.APPLICATION_JSON));

        //브랜드명 누락
        ResultActions result4 = mockMvc.perform(MockMvcRequestBuilders
                .get("/product/lowest-price")
                .param("category", "top, outer, pants, sneakers, bag, cap, socks, accessories")
                .param("brand", "A, B, C, D, E, F,, H")
                .contentType(MediaType.APPLICATION_JSON));
        //브랜드명 공백
        ResultActions result5 = mockMvc.perform(MockMvcRequestBuilders
                .get("/product/lowest-price")
                .param("category", "top, outer, pants, sneakers, bag, cap, socks, accessories")
                .param("brand", "A, B, C, D, E, F, , H")
                .contentType(MediaType.APPLICATION_JSON));
        //브랜드명 50글자 초과
        ResultActions result6 = mockMvc.perform(MockMvcRequestBuilders
                .get("/product/lowest-price")
                .param("category", "top, outer, pants, sneakers, bag, cap, socks, accessories")
                .param("brand", "A, B, C, D, E, F, abcdefghijabcdefghijabcdefghijabcdefghijabcdefghij1, H")
                .contentType(MediaType.APPLICATION_JSON));

        //카테고리명 == 브랜드명 갯수 불일치
        ResultActions result7 = mockMvc.perform(MockMvcRequestBuilders
                .get("/product/lowest-price")
                .param("category", "top, outer, pants, sneakers, bag, cap, socks, accessories")
                .param("brand", "A, B, C, D, E, F")
                .contentType(MediaType.APPLICATION_JSON));

        //then
        //카테고리명 누락
        result1.andExpect(status().isBadRequest());
        //카테고리명 공백
        result2.andExpect(status().isBadRequest());
        //유효하지 않은 카테고리명
        result3.andExpect(status().isBadRequest());

        //브랜드명 누락
        result4.andExpect(status().isBadRequest());
        //브랜드명 공백
        result5.andExpect(status().isBadRequest());
        //브랜드명 50글자 초과
        result6.andExpect(status().isBadRequest());
        //카테고리명 == 브랜드명 갯수 불일치
        result7.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("최저가 조회. 요청 성공")
    void searchLowestPriceProductByAllCategories_ok() throws Exception {
        //given
        Mockito.when(productService.searchLowestPriceProductByAllCategories(any(LowestPriceProductRequest.class)))
                .thenReturn(getStubLowestPriceProductDto());

        //when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .get("/product/lowest-price")
                .param("category", "top, outer, pants, sneakers, bag, cap, socks, accessories")
                .param("brand", "A, B, C, D, E, F, G, H")
                .contentType(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPrice", is(81000)));
    }

    @Test
    @DisplayName("한 브랜드 최저가 조회. 잘못 된 파라미터값으로 요청이 올 경우, 요청이 실패하고 400 에러 코드 리턴")
    void searchLowestTotalPriceProductByBrand_fail() throws Exception {
        //given

        //when
        //브랜드명 누락
        ResultActions result1 = mockMvc.perform(MockMvcRequestBuilders
                .get("/product/lowest-total-price")
                .param("brand", "")
                .contentType(MediaType.APPLICATION_JSON));
        //브랜드명 공백
        ResultActions result2 = mockMvc.perform(MockMvcRequestBuilders
                .get("/product/lowest-total-price")
                .param("brand", " ")
                .contentType(MediaType.APPLICATION_JSON));
        //브랜드명 50글자 초과
        ResultActions result3 = mockMvc.perform(MockMvcRequestBuilders
                .get("/product/lowest-total-price")
                .param("brand", "abcdefghijabcdefghijabcdefghijabcdefghijabcdefghij1")
                .contentType(MediaType.APPLICATION_JSON));

        //then
        //브랜드명 누락
        result1.andExpect(status().isBadRequest());
        //브랜드명 공백
        result2.andExpect(status().isBadRequest());
        //브랜드명 50글자 초과
        result3.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("한 브랜드 최저가 조회. 요청 성공")
    void searchLowestTotalPriceProductByBrand_ok() throws Exception {
        //given
        String brand = "A";

        Mockito.when(productService.searchLowestTotalPriceProductByBrand(anyString()))
                .thenReturn(getStubLowestPriceProductDtoSearchByBrand());

        //when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .get("/product/lowest-total-price")
                .param("brand", brand)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPrice", is(81000)));
    }

    private LowestPriceProductDto getStubLowestPriceProductDto() {
        LowestPriceProductDto lowestPriceProductDto = new LowestPriceProductDto();
        List<ProductInfo> productInfos = Arrays.asList(
                new ProductInfo(1L, Category.TOP, "A", 10000),
                new ProductInfo(2L, Category.OUTER, "B", 20000),
                new ProductInfo(3L, Category.PANTS, "C", 5000),
                new ProductInfo(4L, Category.SNEAKERS, "D", 7000),
                new ProductInfo(5L, Category.BAG, "E", 8000),
                new ProductInfo(6L, Category.CAP, "F", 10000),
                new ProductInfo(7L, Category.SOCKS, "G", 12000),
                new ProductInfo(8L, Category.ACCESSORIES, "H", 9000)
        );

        productInfos.stream()
                .forEach(productInfo -> lowestPriceProductDto.addLowestPriceProductInCategory(productInfo));

        return lowestPriceProductDto;
    }

    private LowestPriceProductDto getStubLowestPriceProductDtoSearchByBrand() {
        LowestPriceProductDto lowestPriceProductDto = new LowestPriceProductDto();
        List<ProductInfo> productInfos = Arrays.asList(
                new ProductInfo(1L, Category.TOP, "A", 10000),
                new ProductInfo(2L, Category.OUTER, "A", 20000),
                new ProductInfo(3L, Category.PANTS, "A", 5000),
                new ProductInfo(4L, Category.SNEAKERS, "A", 7000),
                new ProductInfo(5L, Category.BAG, "A", 8000),
                new ProductInfo(6L, Category.CAP, "A", 10000),
                new ProductInfo(7L, Category.SOCKS, "A", 12000),
                new ProductInfo(8L, Category.ACCESSORIES, "A", 9000)
        );

        productInfos.stream()
                .forEach(productInfo -> lowestPriceProductDto.addLowestPriceProductInCategory(productInfo));

        return lowestPriceProductDto;
    }

    private LowestPriceProductRequest getStubLowestPriceProductRequest(List<String> brands, List<String> categories) {
        return LowestPriceProductRequest.builder()
                .brand(brands)
                .category(categories)
                .build();
    }

    private ProductPostRequest getStubProductPostRequest(String category, String brand, Integer price) {
        return new ProductPostRequest(category, brand, price);
    }

    private Product getStubProduct(ProductPostRequest stubProductPostRequest) {
        return new Product(stubProductPostRequest);
    }
}
