package com.vuebackend.gateway;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import com.vuebackend.entitiydata.DatapointData;

@FeignClient("vue-backend-websocket")
public interface WebsocketClient {
    @RequestMapping(value = "/datapoint", method = RequestMethod.PUT)
    Object publishDatapoint(@RequestBody DatapointData datapoint);
}