package com.exa.gatewayservice.feignClient;

import com.exa.gatewayservice.dto.CreateUserDTO;
import com.exa.gatewayservice.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service", url = "${spring.cloud.gateway.server.webmvc.routes[0].uri}")
public interface UserFeignClient {

    @PostMapping("/users")
    ResponseEntity<UserDTO> createUser(@RequestBody CreateUserDTO createUserDTO);
}
