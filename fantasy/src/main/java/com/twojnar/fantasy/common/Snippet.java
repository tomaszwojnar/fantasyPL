package com.twojnar.fantasy.common;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

public class Snippet {
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
}

