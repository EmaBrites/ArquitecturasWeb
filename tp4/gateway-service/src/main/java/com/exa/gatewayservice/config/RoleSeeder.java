package com.exa.gatewayservice.config;

import com.exa.gatewayservice.entity.Role;
import com.exa.gatewayservice.repository.RoleRepository;
import com.exa.gatewayservice.security.AuthorityConstant;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleSeeder {

    private final RoleRepository roleRepository;

    @PostConstruct
    public void seedRoles() {
        createRoleIfNotExists(AuthorityConstant.USER.getAuthority());
        createRoleIfNotExists(AuthorityConstant.ADMIN.getAuthority());
    }

    private void createRoleIfNotExists(String roleName) {
        if (roleRepository.findByName(roleName) == null) {
            Role role = new Role();
            role.setName(roleName);
            roleRepository.save(role);
        }
    }
}