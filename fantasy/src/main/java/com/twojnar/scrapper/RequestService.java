package com.twojnar.scrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RequestService {
	
	private RestTemplate restTemplate;
	
	@Autowired
	public RequestService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder
                .errorHandler(new RestTemplateResponseErrorHandler())
                .build();
          }

	public String sendRequest(Request request) {
		return request.send();
	}
	
	public Request defineRequest(String uri) {
		return new Request(uri, restTemplate);
	}
	

}
