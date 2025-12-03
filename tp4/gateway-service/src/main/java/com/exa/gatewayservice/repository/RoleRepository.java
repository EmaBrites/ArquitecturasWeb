package com.exa.gatewayservice.repository;

import com.exa.gatewayservice.dto.RoleDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import com.exa.gatewayservice.entity.Role;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(String name);

    @Query("SELECT new com.exa.gatewayservice.dto.RoleDTO(r.name) FROM Role r")
    List<RoleDTO> getAllRoles();
}
