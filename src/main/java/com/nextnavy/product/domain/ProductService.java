package com.nextnavy.product.domain;

import org.springframework.stereotype.Service;

import com.nextnavy.product.domain.dto.ProductResisterRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductService {

	private final ProductRepository productRepository;

	public Long resisterProduct(final ProductResisterRequest request) {
		Product product = request.toProduct();
		return productRepository.save(product).getId();
	}
}
