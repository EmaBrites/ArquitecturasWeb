package com.exa.gatewayservice.feignClient;

import com.exa.gatewayservice.dto.UserDetailsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "${spring.cloud.gateway.server.webmvc.routes[0].uri}")
public interface UserFeignClient {

    @GetMapping("/users/username/{username}")
    ResponseEntity<UserDetailsDTO> getUserByUsername(@PathVariable String username);
}
