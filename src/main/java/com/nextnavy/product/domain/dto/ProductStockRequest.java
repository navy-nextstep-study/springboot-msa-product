package com.nextnavy.product.domain.dto;

import java.util.List;

public record ProductStockRequest(
	List<ProductStock> productStocks
) {
	public record ProductStock(
		Long productId,
		int quantity
	) {}
}
