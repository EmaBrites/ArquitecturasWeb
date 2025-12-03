package com.exa.gatewayservice.repository;

import com.exa.gatewayservice.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Integer> {
    @Query("SELECT u FROM AuthUser u WHERE u.username = :username")
    AuthUser findUserEntityByUsername(@Param("username") String username);
}
