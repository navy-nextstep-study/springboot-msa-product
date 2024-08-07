package com.nextnavy.product.mock;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mock")
public class MockController {

	@GetMapping("/products/{id}")
	public MockRequest getProduct(@PathVariable Long id) {
		return new MockRequest("모탠다드", 20000, 10);
	}

	@PatchMapping("/products/{id}")
	public void updateStock(@PathVariable Long id, @RequestParam int quantity) {
		System.out.println("재고 갱신 요청: id=" + id + ", quantity=" + quantity);
	}
}
