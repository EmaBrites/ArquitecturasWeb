package com.exa.gatewayservice.feignClient;

import com.exa.gatewayservice.dto.CreateUserDTO;
import com.exa.gatewayservice.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name ="user-service", url = "http://localhost:8002")
public interface UserClient {
    @PostMapping("/users")
    UserDTO createUser(@RequestBody CreateUserDTO dto);
}
