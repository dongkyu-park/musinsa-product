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
    void fail() throws Exception {
        //given
        String zeroString = "";
        String blank = " ";

        //when
        //then
        //카테고리명 누락
        mockMvc.perform(MockMvcRequestBuilders
                .post("/product").param("category", zeroString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        //카테고리명 공백
        mockMvc.perform(MockMvcRequestBuilders
                .post("/product").param("category", blank)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        //브랜드명 누락
        mockMvc.perform(MockMvcRequestBuilders
                .post("/product").param("brand", zeroString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        //브랜드명 공백
        mockMvc.perform(MockMvcRequestBuilders
                .post("/product").param("brand", blank)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        //브랜드명 50글자 초과
        mockMvc.perform(MockMvcRequestBuilders
                .post("/product").param("brand", "abcdefghijabcdefghijabcdefghijabcdefghijabcdefghij1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        //가격 누락
        mockMvc.perform(MockMvcRequestBuilders
                .post("/product").param("price", zeroString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        //가격 공백
        mockMvc.perform(MockMvcRequestBuilders.post("/product").param("price", blank)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        //가격 음수
        mockMvc.perform(MockMvcRequestBuilders
                .post("/product").param("price", "-10000")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("요청 성공")
    void ok() throws Exception {
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
