package com.exa.accountservice.entity;

import com.exa.accountservice.enums.AccountStateEnum;
import com.exa.accountservice.enums.AccountTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Account {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false)
    private Integer userId;
    @Column(nullable = false)
    private LocalDateTime createdDate;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private AccountTypeEnum accountType;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private AccountStateEnum accountState;
    @Column(nullable = false)
    private Double balance;
}
