package com.sallics.search.volume.Clients;


import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;


/**
 * Webclient class create customised RestTemplate and performs external HTTP calls to Amazon
 *
 *
 * @author mustafa
 *
 */
@Component
public class AmazonClient {


    /**
     * make Http call to Amazon http://completion.amazon.com/search/complete and return the response as String
     * @param keyword
     * @return Response body as String
     * @throws Exception
     */
    public String callAmazonCompletionService(String keyword) throws Exception{

            String url = (String.format("http://completion.amazon.com/search/complete?search-alias=aps&client=amazon-search-ui&mkt=1&q=%s", keyword.replace(" ","+")));

            RestTemplate restTemplate = createRestTemplate(10000);

            RequestEntity request = RequestEntity.get(new URI(url)).build();
            ResponseEntity<String> responseEntity = restTemplate.exchange(request, String.class);

            return responseEntity.getBody();

    }


    /**
     * creates and configures a RestTemplate with a timeout
     * can be expanded to return more customised restTemplate
     * @param time
     * @return {@link RestTemplate}
     */
    public RestTemplate createRestTemplate(int time) {
        RestTemplate restTemplate = new RestTemplate();
        ((SimpleClientHttpRequestFactory) restTemplate.getRequestFactory()).setConnectTimeout(time);
        ((SimpleClientHttpRequestFactory) restTemplate.getRequestFactory()).setReadTimeout(time);

        return restTemplate;
    }







}
