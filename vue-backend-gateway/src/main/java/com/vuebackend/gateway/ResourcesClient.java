package com.vuebackend.gateway;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import com.vuebackend.communication.AddDatapointRequest;
import com.vuebackend.communication.AddEndpointRequest;
import com.vuebackend.communication.LoginRequest;
import com.vuebackend.communication.RegisterRequest;

import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("vue-backend-resources")
public interface ResourcesClient {
    
    @RequestMapping(value = "/user/checkCredentials", method = RequestMethod.POST)
    Object checkCredentials(@RequestBody LoginRequest loginRequest);

    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    Object registerUser(@RequestBody RegisterRequest registerRequest);

    @RequestMapping(value = "/endpoint", method = RequestMethod.POST)
    Object addEndpoint(@RequestBody AddEndpointRequest request,
                       @RequestHeader("Authorization") String headerValue);

    @RequestMapping(value = "/endpoint/all", method = RequestMethod.GET)
    Object getEndpoints(@RequestHeader("Authorization") String token);

    @RequestMapping(value = "/endpoint/{endpointName}", method = RequestMethod.DELETE)
    Object deleteEndpoint(@PathVariable(value="endpointName") String endpointName,
                          @RequestHeader("Authorization") String token);

    @RequestMapping(value = "/sensor", method = RequestMethod.POST)
    Object addSensors(@RequestBody AddEndpointRequest request,
                      @RequestHeader("Authorization") String token);

    @RequestMapping(value = "/sensor/{endpointName}/{sensorName}", method = RequestMethod.DELETE)
    Object deleteSensor(@PathVariable(value="endpointName") String endpointName,
                        @PathVariable(value="sensorName") String sensorName,
                        @RequestHeader("Authorization") String token);

    @RequestMapping(value = "/data", method = RequestMethod.POST)
    Object addDatapoint(@RequestBody AddDatapointRequest request,
                        @RequestHeader("Authorization") String token);
}