package com.nextnavy.product.domain.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "price")
	private int price;

	@Column(name = "stock")
	private int stock;

	@Enumerated(EnumType.STRING)
	@Column(name = "source")
	private productSource source;

	@Column(name = "source_id")
	private Long sourceId;

	public Product(final String name, final int price, final int stock) {
		this.name = name;
		this.price = price;
		this.stock = stock;
		this.source = productSource.INTERNAL;
	}

	public Product(final Long sourceId) {
		this.source = productSource.EXTERNAL;
		this.sourceId = sourceId;
	}

	public void updateStock(final int quantity) {
		this.stock += quantity;
	}

	public boolean isInternal() {
		return this.source.isInternal();
	}
}
