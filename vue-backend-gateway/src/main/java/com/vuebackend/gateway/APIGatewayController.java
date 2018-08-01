package com.vuebackend.gateway;

import java.net.MalformedURLException;
import java.util.List;

import com.vuebackend.communication.AddDatapointRequest;
import com.vuebackend.communication.AddEndpointRequest;
import com.vuebackend.communication.FailureResponseMessage;
import com.vuebackend.communication.LoginRequest;
import com.vuebackend.communication.RegisterRequest;
import com.vuebackend.communication.TokenRequest;
import com.vuebackend.entitiydata.DatapointData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.RestTemplate;

@Controller
@CrossOrigin
public class APIGatewayController {

    @Autowired
    private ResourcesClient resourcesClient;

    @Autowired
    private AuthClient authClient;

    @Autowired
    private WebsocketClient websocketClient;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Value("${resourceServerName}")
    private String resourceServerName;

    @Bean
    private RestTemplate buildRestTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    // Gateway API for users

    @PutMapping("/user")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(this.authClient.login(loginRequest));
    }

    @PostMapping("/user")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(this.authClient.register(registerRequest));
    }

    @GetMapping("/{username}/endpoint/all") 
    public ResponseEntity<?> getAllEndpoints(@PathVariable(value="username") String username) {
        return ResponseEntity.ok(this.resourcesClient.getEndpoints(username));
    }

    @PostMapping("/data")
    public ResponseEntity<?> addDatapoint(@RequestBody AddDatapointRequest request) {
        return ResponseEntity.ok(this.resourcesClient.addDatapoint(request));
    }

    @PostMapping("/endpoint")
    public ResponseEntity<?> addEndpoint(@RequestBody AddEndpointRequest request,
                       @RequestHeader("Authorization") String headerValue) {

        return ResponseEntity.ok(this.resourcesClient.addEndpoint(request, headerValue));
    }

    @PostMapping("/sensor")
    public ResponseEntity<?> addSensor(@RequestBody AddEndpointRequest request) {
        return ResponseEntity.ok(this.resourcesClient.addSensor(request));
    }

    @GetMapping("/websocket")
    public ResponseEntity<?> getWebsocketServerAddr() throws MalformedURLException {
        
        List<ServiceInstance> services = this.discoveryClient.getInstances("vue-backend-websocket");
        String addr;

        if(services.size() == 0) {
            return ResponseEntity.ok(new FailureResponseMessage());
        }

        ServiceInstance service = services.get((int)Math.round(Math.random() * (services.size() - 1)));
        addr = service.getUri().toURL().toString();
        return ResponseEntity.ok(new Object() { 
            @SuppressWarnings("unused")           
            public String websocketUrl = addr;
        });
    }

    @PostMapping("/user/isTokenValid")
    public ResponseEntity<?> isTokenValid(@RequestBody TokenRequest request) {
        return ResponseEntity.ok(this.authClient.isTokenValid(request));
    }

    
    // Gateway API for backend

    @PostMapping("/user/checkCredentials")
    public ResponseEntity<?> checkCredentials(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(this.resourcesClient.checkCredentials(loginRequest));
    }

    @PostMapping("/user/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(this.resourcesClient.registerUser(registerRequest));
    }

    @PutMapping("/datapoint")
    public ResponseEntity<?> publishDatapoint(@RequestBody DatapointData datapoint) {
        return ResponseEntity.ok(this.websocketClient.publishDatapoint(datapoint));
    }
}