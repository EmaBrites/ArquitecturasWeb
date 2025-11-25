package com.exa.gatewayservice.service;

import com.exa.gatewayservice.dto.UserDetailsDTO;
import com.exa.gatewayservice.feignClient.UserFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final UserFeignClient userFeignClient;

    public DomainUserDetailsService(UserFeignClient userFeignClient) {
        this.userFeignClient = userFeignClient;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Loading user by username: {}", username);
        ResponseEntity<UserDetailsDTO> response = userFeignClient.getUserByUsername(username);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw  new UsernameNotFoundException("Username not found");
        }
        UserDetailsDTO userDetails = userFeignClient.getUserByUsername(username).getBody();
        return User.withUsername(userDetails.getUsername())
                .password(userDetails.getPassword())
                .roles(userDetails.getRoles().stream().map(Enum::name).toArray(String[]::new))
                .build();
    }
}
