package com.exa.accountservice.repository;

import com.exa.accountservice.dto.AccountTransactionDTO;
import com.exa.accountservice.entity.AccountTransaction;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AccountTransactionRepository extends JpaRepository<AccountTransaction, Integer> {
    @Query("SELECT new com.exa.accountservice.dto.AccountTransactionDTO(at.id, at.account.id, at.transactionType, at.amount, at.dateTime) " +
           "FROM AccountTransaction at " +
           "WHERE at.dateTime BETWEEN :dateTimeAfter AND :dateTimeBefore")
    List<AccountTransactionDTO> findAccountTransactionsByDateTimeBetween(LocalDateTime dateTimeAfter, LocalDateTime dateTimeBefore, Sort sort);

    @Query("""
            SELECT SUM(at.amount) FROM AccountTransaction at
                    WHERE at.dateTime BETWEEN :dateTimeAfter AND :dateTimeBefore
                                AND at.transactionType = com.exa.accountservice.enums.TransactionTypeEnum.CHARGE
            """)
    Double calculateTotalRevenue(LocalDateTime dateTimeAfter, LocalDateTime dateTimeBefore);
}
