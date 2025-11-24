package com.exa.accountservice.service;

import com.exa.accountservice.dto.AccountDTO;
import com.exa.accountservice.dto.AccountTransactionDTO;
import com.exa.accountservice.dto.TransactionDTO;
import com.exa.accountservice.entity.Account;
import com.exa.accountservice.entity.AccountTransaction;
import com.exa.accountservice.enums.TransactionTypeEnum;
import com.exa.accountservice.repository.AccountTransactionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AccountTransactionService {

    private final AccountTransactionRepository accountTransactionRepository;
    private final AccountService accountService;

    public AccountTransactionService(AccountService accountService, AccountTransactionRepository accountTransactionRepository) {
        this.accountTransactionRepository = accountTransactionRepository;
        this.accountService = accountService;
    }

    public AccountTransactionDTO topupAccount(TransactionDTO transactionDTO)
            throws AccountNotFoundException, IllegalArgumentException, IllegalStateException {
        validateAmount(transactionDTO.amount());
        AccountDTO accountDTO = accountService.updateAccountBalance(transactionDTO.accountId(), transactionDTO.amount());
        return recordTransaction(accountDTO, transactionDTO.amount(), TransactionTypeEnum.DEPOSIT);
    }

    public AccountTransactionDTO chargeAccount(TransactionDTO transactionDTO)
            throws AccountNotFoundException, IllegalArgumentException, IllegalStateException {
        validateAmount(transactionDTO.amount());
        AccountDTO accountDTO = accountService.updateAccountBalance(transactionDTO.accountId(), -transactionDTO.amount());
        return recordTransaction(accountDTO, transactionDTO.amount(), TransactionTypeEnum.CHARGE);
    }

    public List<AccountTransactionDTO> findAccountTransactionsByDateTimeBetween(LocalDate dateAfter, LocalDate dateBefore) {
        validateDateRange(dateAfter, dateBefore);
        LocalDateTime dateTimeAfter = dateAfter.atStartOfDay();
        LocalDateTime dateTimeBefore = dateBefore.plusDays(1).atStartOfDay();
        return accountTransactionRepository.findAccountTransactionsByDateTimeBetween(dateTimeAfter, dateTimeBefore, Sort.by("dateTime").ascending());
    }

    private void validateDateRange(LocalDate dateAfter, LocalDate dateBefore) {
        if (dateAfter.isAfter(dateBefore)) {
            throw new IllegalArgumentException("dateAfter must not be after dateBefore");
        }
    }

    private void validateAmount(double amount) throws IllegalArgumentException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
    }

    private AccountTransactionDTO recordTransaction(AccountDTO accountDTO, Double amount, TransactionTypeEnum type) {
        Account account = new Account();
        BeanUtils.copyProperties(accountDTO, account);
        AccountTransaction accountTransaction = new AccountTransaction();
        accountTransaction.setAccount(account);
        accountTransaction.setTransactionType(type);
        accountTransaction.setAmount(amount);
        accountTransaction.setDateTime(LocalDateTime.now());
        AccountTransaction accountTransactionSaved = accountTransactionRepository.save(accountTransaction);
        AccountTransactionDTO accountTransactionDTO = new AccountTransactionDTO();
        BeanUtils.copyProperties(accountTransactionSaved, accountTransactionDTO);
        accountTransactionDTO.setAccountId(accountTransactionSaved.getAccount().getId());
        return accountTransactionDTO;
    }

    public Double calculateTotalRevenue(LocalDate dateAfter, LocalDate dateBefore) {
        validateDateRange(dateAfter, dateBefore);
        LocalDateTime dateTimeAfter = dateAfter.atStartOfDay();
        LocalDateTime dateTimeBefore = dateBefore.plusDays(1).atStartOfDay();
        Double calculatedTotalRevenue = accountTransactionRepository.calculateTotalRevenue(dateTimeAfter, dateTimeBefore);
        if (calculatedTotalRevenue == null) {
            throw  new IllegalArgumentException("No revenue data found for the given date range");
        }
        return calculatedTotalRevenue;
    }
}
