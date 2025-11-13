package com.exa.gatewayservice.service;

import com.exa.gatewayservice.dto.CreateUserDTO;
import com.exa.gatewayservice.dto.UserDTO;
import com.exa.gatewayservice.feignClient.UserFeignClient;
import org.springframework.stereotype.Service;

@Service
public class LogUpService {
    UserFeignClient userFeignClient;

    public LogUpService(UserFeignClient userFeignClient) {
        this.userFeignClient = userFeignClient;
    }

    public UserDTO createUser(CreateUserDTO createUserDTO) {
        return userFeignClient.createUser(createUserDTO).getBody();
    }
}
