package com.twojnar.scrapper;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

@Component
public class RestTemplateResponseErrorHandler 
  implements ResponseErrorHandler {
 
    @Override
    public boolean hasError(ClientHttpResponse httpResponse) 
      throws IOException {
 
        return (
          httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR 
          || httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR);
    }
 
    @Override
    public void handleError(ClientHttpResponse httpResponse) 
      throws IOException {
 
        if (httpResponse.getStatusCode()
          .series() == HttpStatus.Series.SERVER_ERROR) {
            System.out.println(httpResponse.getBody().readAllBytes().toString());
        } else if (httpResponse.getStatusCode()
          .series() == HttpStatus.Series.CLIENT_ERROR) {
        	System.out.println(httpResponse.getBody().readAllBytes().toString());
            if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
            	System.out.println(httpResponse.getBody().readAllBytes().toString());
            }
        }
    }
}