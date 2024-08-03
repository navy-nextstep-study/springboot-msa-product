package com.nextnavy.product.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import com.nextnavy.product.domain.service.MockClient;

@Configuration
public class RestClientConfig {

	@Bean
	public MockClient mockClient() {
		RestClient restClient = RestClient.builder()
			.baseUrl("http://localhost:8080/mock")
			.build();
		RestClientAdapter restClientAdapter = RestClientAdapter.create(restClient);
		HttpServiceProxyFactory serviceProxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();

		return serviceProxyFactory.createClient(MockClient.class);
	}
}
