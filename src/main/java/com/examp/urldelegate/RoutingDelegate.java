package com.examp.urldelegate;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Service
public class RoutingDelegate {
    private final String baseServerUrl = "https://httpbin.org";

    public ResponseEntity<String> redirectPost( HttpServletRequest request, HttpServletResponse response){
        String responseString = "";
        try {
            // build up the redirect URL
            String queryString = request.getQueryString();
            String redirectUrl = baseServerUrl + request.getRequestURI() +
                    (queryString != null ? "?" + queryString : "");
            responseString = forwardPostRequest(redirectUrl, request);
        }
        catch (Exception e) {
            return new ResponseEntity<String>("REDIRECT ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>(responseString, HttpStatus.OK);
    }

    private String forwardPostRequest(String url, HttpServletRequest request){
        String responseString = "";
        HttpHeaders headers = new HttpHeaders();
        //headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // rebuild map from params
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        for ( Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            String key = entry.getKey();
            String[] values = entry.getValue();
            map.add(key, values[0]);
        }

        HttpEntity<MultiValueMap<String, String>> newRequest = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange( url,HttpMethod.GET, newRequest , String.class );
        responseString = responseEntity.getBody();
        return responseString;
    }

}
