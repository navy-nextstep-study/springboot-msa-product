package com.nextnavy.product.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.nextnavy.product.domain.domain.Product;
import com.nextnavy.product.domain.domain.ProductRepository;
import com.nextnavy.product.domain.dto.ProductInfo;
import com.nextnavy.product.domain.dto.ProductResisterRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductService {

	private final ProductRepository productRepository;
	private final MockClient mockClient;

	public Long resisterProduct(final ProductResisterRequest request) {
		Product product = request.toProduct();
		return productRepository.save(product).getId();
	}

	public ProductInfo getProduct(final Long productId) {
		/*
		 TODO: 서킷 브레이커 추가
		 TODO: 유량 제어 추가
		*/
		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new IllegalArgumentException("Product not found. productId: " + productId));

		if (product.isInternal()) {
			return ProductInfo.from(product);
		}

		return ProductInfo.of(productId, mockClient.getExternalProduct(product.getSourceId()));
	}

	@Transactional
	public void updateStock(final Long productId, final int quantity) {
		/*
		 TODO: 주문 취소 이벤트 발생 시 재고 복원 로직 추가
		 TODO: 결제 취소 이벤트 발생 시 재고 복원 로직 추가
		 TODO: 취소 이벤트에 대한 중복 처리 방지 로직 추가
		*/
		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new IllegalArgumentException("Product not found. productId: " + productId));

		if (product.isInternal()) {
			product.updateStock(quantity);
			return;
		}

		mockClient.updateStock(product.getSourceId(), quantity);
	}
}
