package com.nextnavy.product.domain;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	public Product getProduct(final Long productId) {
		return productRepository.findById(productId)
			.orElseThrow(() -> new IllegalArgumentException("Product not found. productId: " + productId));
	}

	@Transactional
	public void updateStock(final Long productId, final int quantity) {
		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new IllegalArgumentException("Product not found. productId: " + productId));
		product.updateStock(quantity);
	}
}
