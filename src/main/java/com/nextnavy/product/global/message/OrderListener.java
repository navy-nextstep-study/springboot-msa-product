package com.nextnavy.product.global.message;

import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.nextnavy.product.domain.domain.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Profile("!default")
@RequiredArgsConstructor
@Component
public class OrderListener {

	private final ProductRepository productRepository;

	@KafkaListener(topics = "order-cancel")
	public void updateStock(@Payload Message<OrderCancelEvent> message) {
		OrderCancelEvent orderCancelEvent = message.body;

		log.info("orderEvent: {}", orderCancelEvent);

		orderCancelEvent.productStocks().forEach(productStock -> productRepository.findById(productStock.productId())
			.ifPresent(product -> product.updateStock(productStock.quantity())));
	}
}
