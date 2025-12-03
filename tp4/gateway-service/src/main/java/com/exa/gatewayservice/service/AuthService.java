package com.exa.gatewayservice.service;

import com.exa.gatewayservice.dto.*;
import com.exa.gatewayservice.entity.AuthUser;
import com.exa.gatewayservice.entity.Role;
import com.exa.gatewayservice.feignClient.UserClient;
import com.exa.gatewayservice.repository.AuthUserRepository;
import com.exa.gatewayservice.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthService {
    //UserFeignClient userFeignClient;

    private final PasswordEncoder passwordEncoder;
    private final AuthUserRepository authUserRepository;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final UserClient userClient;

    public AuthResponse login(LoginRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        UserDetails user = authUserRepository.findUserEntityByUsername(request.getUsername());

        if(user == null){
            return null;
        }

        String token = jwtService.getToken(user);

        return new AuthResponse(token);
    }

    public AuthResponse signUp(RegisterRequest registerRequest) {
        Role roleFound = roleRepository.findByName(registerRequest.getRoles());

        if(roleFound == null){
            return new AuthResponse("No existe el rol");
        }

        UserDTO userMS = userClient.createUser(registerRequest.getCreateUserDTO());

        AuthUser user = new AuthUser(registerRequest.getUsername(), passwordEncoder.encode(registerRequest.getPassword()),userMS.getId());
        user.addRole(roleFound);

        authUserRepository.save(user);

        return new AuthResponse(jwtService.getToken(user));
    }
}
