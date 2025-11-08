package com.exa.accountservice.entity;

import com.exa.accountservice.enums.TransactionTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    private int id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "accounId", referencedColumnName = "id", nullable = false)
    private Account account;
    @Column(nullable = false)
    private TransactionTypeEnum transactionType;
    @Column(nullable = false)
    private double amount;
    @Column(nullable = false)
    private LocalDateTime dateTime;
}
