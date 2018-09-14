package com.twojnar.scrapper;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import com.twojnar.fantasy.common.FantasyStatus;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {
	
	static final Logger logger = LoggerFactory.getLogger(FantasyStatus.class);
 
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
            logger.error("Server responded with :" + httpResponse.getStatusCode() + httpResponse.getStatusText());
        } else if (httpResponse.getStatusCode()
          .series() == HttpStatus.Series.CLIENT_ERROR) {
            logger.error("Server responded with :" + httpResponse.getStatusCode() + httpResponse.getStatusText());
        }
    }
}