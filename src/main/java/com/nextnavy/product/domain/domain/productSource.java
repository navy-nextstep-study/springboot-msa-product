package com.nextnavy.product.domain.domain;

public enum productSource {
	EXTERNAL, INTERNAL;

	public boolean isInternal() {
		return this == INTERNAL;
	}
}
