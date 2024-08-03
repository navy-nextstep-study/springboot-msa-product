package com.nextnavy.product.domain.dto;

import com.nextnavy.product.domain.Product;

public record ProductResisterRequest(
	String name,
	int price,
	int stock
) {
	public Product toProduct() {
		return new Product(name, price, stock);
	}
}
