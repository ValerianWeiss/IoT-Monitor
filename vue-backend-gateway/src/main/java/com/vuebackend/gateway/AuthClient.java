package com.vuebackend.gateway;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import com.vuebackend.communication.LoginRequest;
import com.vuebackend.communication.RegisterRequest;

@FeignClient("vue-backend-auth")
public interface AuthClient {
    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    Object login(@RequestBody LoginRequest loginRequest);

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    Object register(@RequestBody RegisterRequest registerRequest);
}