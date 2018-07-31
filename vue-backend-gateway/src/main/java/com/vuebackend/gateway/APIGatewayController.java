package com.vuebackend.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

@Controller
public class APIGatewayController {

    @Autowired
    private ResourcesClient resourcesClient;

    @Value("${resourceServerName}")
    private String resourceServerName;

    @Bean
    private RestTemplate buildRestTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @GetMapping("/{username}/endpoint/all") 
    ResponseEntity<?> getAllEndpoints(@PathVariable(value="username") String username) {
        return ResponseEntity.ok(this.resourcesClient.getEndpoints(username));
    }
}