package com.nextnavy.product.domain.service;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import com.nextnavy.product.domain.dto.ExternalProduct;

@Component
@HttpExchange
public interface MockClient {

	@GetExchange("/products/{id}")
	ExternalProduct getExternalProduct(@PathVariable Long id);

	@PatchMapping("/products/{id}")
	void updateStock(@PathVariable Long id, @RequestParam int quantity);
}
