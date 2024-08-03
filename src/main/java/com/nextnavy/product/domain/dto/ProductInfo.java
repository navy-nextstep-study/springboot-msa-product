package com.nextnavy.product.domain.dto;

import com.nextnavy.product.domain.domain.Product;

public record ProductInfo(
	Long id,
	String name,
	int price,
	int stock
) {
	public static ProductInfo from(final Product product) {
		return new ProductInfo(product.getId(), product.getName(), product.getPrice(), product.getStock());
	}

	public static ProductInfo of(final Long productId, final ExternalProduct externalProduct) {
		return new ProductInfo(productId, externalProduct.name(), externalProduct.price(), externalProduct.stock());
	}
}
