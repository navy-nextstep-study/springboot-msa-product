package com.nextnavy.product.global.message;

import java.util.List;

public record OrderCancelEvent (
	Long memberId,
	Long orderId,
	List<ProductStock> productStocks,
	Long outboxId
) {
	record ProductStock(Long productId, int quantity) {
	}
}
