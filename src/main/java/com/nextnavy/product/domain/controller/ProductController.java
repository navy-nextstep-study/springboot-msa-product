package com.nextnavy.product.domain.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nextnavy.product.domain.service.ProductService;
import com.nextnavy.product.domain.dto.ProductGetResponse;
import com.nextnavy.product.domain.dto.ProductResisterRequest;
import com.nextnavy.product.domain.dto.ProductResisterResponse;
import com.nextnavy.product.domain.dto.ProductStockRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {

	private final ProductService productService;

	@PostMapping
	public ResponseEntity<ProductResisterResponse> resisterProduct(@RequestBody ProductResisterRequest request) {
		return ResponseEntity.ok(new ProductResisterResponse(productService.resisterProduct(request)));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductGetResponse> getProduct(@PathVariable Long id) {
		return ResponseEntity.ok(ProductGetResponse.from(productService.getProduct(id)));
	}

	@PatchMapping("/{id}/stock")
	public ResponseEntity<Void> updateStock(@PathVariable Long id, @RequestBody ProductStockRequest request) {
		productService.updateStock(id, request.quantity());
		return ResponseEntity.ok().build();
	}
}
