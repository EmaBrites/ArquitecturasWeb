package com.exa.accountservice.service;

import com.exa.accountservice.dto.AccountDTO;
import com.exa.accountservice.dto.AccountTransactionDTO;
import com.exa.accountservice.dto.TransactionDTO;
import com.exa.accountservice.entity.Account;
import com.exa.accountservice.entity.AccountTransaction;
import com.exa.accountservice.enums.TransactionTypeEnum;
import com.exa.accountservice.repository.AccountTransactionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import java.time.LocalDateTime;

@Service
@Transactional
public class AccountTransactionService {

    private final AccountTransactionRepository accountTransactionRepository;
    private final AccountService accountService;

    public AccountTransactionService(AccountService accountService, AccountTransactionRepository accountTransactionRepository) {
        this.accountTransactionRepository = accountTransactionRepository;
        this.accountService = accountService;
    }

    public AccountTransactionDTO topupAccount(TransactionDTO transactionDTO) throws AccountNotFoundException, IllegalArgumentException {
        validateAmount(transactionDTO.amount());
        accountService.updateAccountBalance(transactionDTO.accountId(), transactionDTO.amount());
        return recordTransaction(transactionDTO.accountId(), TransactionTypeEnum.DEPOSIT, transactionDTO.amount());
    }

    public AccountTransactionDTO chargeAccount(TransactionDTO transactionDTO) throws AccountNotFoundException, IllegalArgumentException {
        validateAmount(transactionDTO.amount());
        accountService.updateAccountBalance(transactionDTO.accountId(), -transactionDTO.amount());
        return recordTransaction(transactionDTO.accountId(), TransactionTypeEnum.CHARGE, transactionDTO.amount());
    }

    private void validateAmount(double amount) throws IllegalArgumentException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
    }

    private AccountTransactionDTO recordTransaction(Integer accountId, TransactionTypeEnum type, double amount) throws AccountNotFoundException {
        AccountDTO accountDTO = accountService.getAccountById(accountId);
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

}
