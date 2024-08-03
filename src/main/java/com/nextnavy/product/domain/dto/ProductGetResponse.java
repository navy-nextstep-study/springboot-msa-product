package com.nextnavy.product.domain.dto;

import com.nextnavy.product.domain.Product;

public record ProductGetResponse(
	Long id,
	String name,
	int price,
	int stock
) {
	public static ProductGetResponse from(final Product product) {
		return new ProductGetResponse(product.getId(), product.getName(), product.getPrice(), product.getStock());
	}
}
