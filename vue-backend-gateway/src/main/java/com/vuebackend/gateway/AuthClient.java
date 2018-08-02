package com.vuebackend.gateway;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.vuebackend.communication.CreateTokenRequest;
import com.vuebackend.communication.LoginRequest;
import com.vuebackend.communication.RegisterRequest;
import com.vuebackend.communication.TokenRequest;

@FeignClient("vue-backend-auth")
public interface AuthClient {
    
    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    Object login(@RequestBody LoginRequest loginRequest);

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    Object register(@RequestBody RegisterRequest registerRequest);

    @RequestMapping(value = "/user/isTokenValid", method = RequestMethod.POST)
    Object isTokenValid(@RequestBody TokenRequest request);

    @RequestMapping(value = "/endpoint/token",
                    method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
                    consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String getDeviceToken(@RequestBody CreateTokenRequest request, 
                          @RequestHeader("Authorization") String token);
}