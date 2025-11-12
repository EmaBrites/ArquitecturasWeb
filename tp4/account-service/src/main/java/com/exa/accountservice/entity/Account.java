package com.exa.accountservice.entity;

import com.exa.accountservice.enums.AccountStateEnum;
import com.exa.accountservice.enums.AccountTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // OK

    @Column(nullable = false)
    private Integer userId; //

    @Column(nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();


    @Enumerated(value = EnumType.STRING)
    private AccountTypeEnum accountType;

    @Column(nullable = false)
    private Double balance; //
}
