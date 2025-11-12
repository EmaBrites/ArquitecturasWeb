package com.exa.accountservice.entity;

import com.exa.accountservice.enums.TransactionTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class AccountTransaction {
    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "accountId", referencedColumnName = "id", nullable = false)
    private Account account;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private TransactionTypeEnum transactionType;
    @Column(nullable = false)
    private Double amount;
    @Column(nullable = false)
    private LocalDateTime dateTime;
}
