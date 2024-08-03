package com.nextnavy.product.domain.dto;

public record ProductGetResponse(
	Long id,
	String name,
	int price,
	int stock
) {
	public static ProductGetResponse from(final ProductInfo productInfo) {
		return new ProductGetResponse(productInfo.id(), productInfo.name(), productInfo.price(), productInfo.stock());
	}
}
