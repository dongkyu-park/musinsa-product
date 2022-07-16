package com.musinsa.product.controller;

import com.musinsa.product.domain.Product;
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

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private CustomValidator customValidator;

    @Test
    @DisplayName("잘못 된 파라미터값으로 요청이 올 경우, 요청이 실패하고 400 에러 코드 리턴")
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
    @DisplayName("요청 성공")
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

    private ProductPostRequest getStubProductPostRequest(String category, String brand, Integer price) {
        return new ProductPostRequest(category, brand, price);
    }

    private Product getStubProduct(ProductPostRequest stubProductPostRequest) {
        return new Product(stubProductPostRequest);
    }
}
