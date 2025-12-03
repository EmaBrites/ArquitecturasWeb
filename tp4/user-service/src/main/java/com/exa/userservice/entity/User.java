package com.exa.userservice.entity;

import com.exa.userservice.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String phone;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @ElementCollection
    @CollectionTable(name = "user_accounts", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "account_id")
    private List<Integer> accountIds = new ArrayList<>();

}
