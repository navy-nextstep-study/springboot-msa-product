package com.nextnavy.product.domain;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nextnavy.product.domain.dto.ProductResisterRequest;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProductControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	ProductRepository productRepository;

	@Test
	void 상품_등록() throws Exception {
		// given
		ProductResisterRequest request = new ProductResisterRequest("농구공", 10000, 100);

		// when
		ResultActions resultActions = mockMvc.perform(post("/api/products")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(request)));

		// then
		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").isNumber());
	}

	@Test
	void 상품_조회() throws Exception {
		// given
		Product product = new Product("농구공", 10000, 100);
		Product savedProduct = productRepository.save(product);

		// when
		ResultActions resultActions = mockMvc.perform(get("/api/products/{id}", savedProduct.getId()));

		// then
		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(savedProduct.getId()))
			.andExpect(jsonPath("$.name").value(savedProduct.getName()))
			.andExpect(jsonPath("$.price").value(savedProduct.getPrice()))
			.andExpect(jsonPath("$.stock").value(savedProduct.getStock()));
	}
}