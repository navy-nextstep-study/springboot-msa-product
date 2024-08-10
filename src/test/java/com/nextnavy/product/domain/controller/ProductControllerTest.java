package com.nextnavy.product.domain.controller;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nextnavy.product.domain.domain.Product;
import com.nextnavy.product.domain.domain.ProductRepository;
import com.nextnavy.product.domain.dto.ExternalProduct;
import com.nextnavy.product.domain.dto.ProductResisterRequest;
import com.nextnavy.product.domain.dto.ProductStockRequest;
import com.nextnavy.product.domain.service.MockClient;

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

	@MockBean
	MockClient mockClient;

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
	void 자사_상품_조회() throws Exception {
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

	@Test
	void 타사_상품_조회() throws Exception {
		// given
		Product product = new Product(1L);
		Product savedProduct = productRepository.save(product);

		given(mockClient.getExternalProduct(anyLong()))
			.willReturn(new ExternalProduct("모탠다드", 20000, 10));

		// when
		ResultActions resultActions = mockMvc.perform(get("/api/products/{id}", savedProduct.getId()));

		// then
		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(savedProduct.getId()))
			.andExpect(jsonPath("$.name").value("모탠다드"))
			.andExpect(jsonPath("$.price").value(20000))
			.andExpect(jsonPath("$.stock").value(10));
	}

	@Test
	void 자사_상품_재고_업데이트() throws Exception {
		// given
		Product product = new Product("농구공", 10000, 100);
		Product savedProduct = productRepository.save(product);

		ProductStockRequest request = new ProductStockRequest(
			List.of(new ProductStockRequest.ProductStock(savedProduct.getId(), -1)));

		// when
		ResultActions resultActions = mockMvc.perform(patch("/api/products/stock", savedProduct.getId())
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(request)));

		// then
		resultActions.andExpect(status().isOk());

		Product updatedProduct = productRepository.findById(savedProduct.getId()).get();
		assertThat(updatedProduct.getStock()).isEqualTo(99);
	}

	@Test
	void 타사_상품_재고_업데이트() throws Exception {
		// given
		Product product = new Product(1L);
		Product savedProduct = productRepository.save(product);

		ProductStockRequest request = new ProductStockRequest(
			List.of(new ProductStockRequest.ProductStock(savedProduct.getId(), -1)));

		willDoNothing().given(mockClient).updateStock(anyLong(), anyInt());

		// when
		ResultActions resultActions = mockMvc.perform(patch("/api/products/stock", savedProduct.getId())
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(request)));

		// then
		resultActions.andExpect(status().isOk());

		verify(mockClient).updateStock(anyLong(), anyInt());
	}
}