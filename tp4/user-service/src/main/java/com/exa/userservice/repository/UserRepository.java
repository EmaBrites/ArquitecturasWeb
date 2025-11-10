package com.exa.userservice.repository;

import com.exa.userservice.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("""
        SELECT u FROM User u
        WHERE (:email IS NULL OR u.email LIKE %:email%)
        AND (:phone IS NULL OR u.phone LIKE %:phone%)
        AND ((:name IS NULL) OR (u.firstName LIKE %:name%) OR (u.lastName LIKE %:name%))
        """)
    Page<User> searchUsers(
            @Param("email") String email,
            @Param("phone") String phone,
            @Param("name") String name,
            Pageable pageable
    );
}

