package com.musinsa.product.controller;

import com.musinsa.product.domain.Product;
import com.musinsa.product.dto.ProductRequest;
import com.musinsa.product.service.ProductService;
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
                .post("/product").param("productType", zeroString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        //카테고리명 공백
        mockMvc.perform(MockMvcRequestBuilders
                .post("/product").param("productType", blank)
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
        String productType = "PANTS";
        String brand = "A";
        Integer price = 20000;
        ProductRequest stubProductRequest = getStubProductRequest(productType, brand, price);
        Product stubProduct = getStubProduct(stubProductRequest);
        String body = "{\n" +
                "\"productType\": \"" + productType + "\",\n" +
                "\"brand\": \"" + brand + "\",\n" +
                "\"price\": " + price + "\n" +
                "}";

        Mockito.when(productService.addProduct(any(ProductRequest.class)))
                .thenReturn(stubProduct);

        //when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .post("/product")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.type", is(productType)))
                .andExpect(jsonPath("$.data.brand", is(brand)))
                .andExpect(jsonPath("$.data.price", is(price)));
    }

    private ProductRequest getStubProductRequest(String productType, String brand, Integer price) {
        return new ProductRequest(productType, brand, price);
    }

    private Product getStubProduct(ProductRequest stubProductRequest) {
        return new Product(stubProductRequest);
    }
}
