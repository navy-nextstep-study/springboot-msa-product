package com.nextnavy.product.domain;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nextnavy.product.domain.dto.ProductGetResponse;
import com.nextnavy.product.domain.dto.ProductResisterRequest;
import com.nextnavy.product.domain.dto.ProductResisterResponse;

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
}
