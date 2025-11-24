package com.exa.accountservice.service;

import com.exa.accountservice.dto.AccountDTO;
import com.exa.accountservice.dto.CreateAccountDTO;
import com.exa.accountservice.dto.UpdateAccountDTO;
import com.exa.accountservice.entity.Account;
import com.exa.accountservice.enums.AccountStateEnum;
import com.exa.accountservice.repository.AccountRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import java.time.LocalDateTime;

@Service
@Transactional
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountDTO createAccount(CreateAccountDTO createAccountDTO) {
        Account account = new Account();
        BeanUtils.copyProperties(createAccountDTO, account);
        account.setCreatedDate(LocalDateTime.now());
        account.setBalance(0.0);
        account.setAccountState(AccountStateEnum.ACTIVE);
        Account savedAccount = accountRepository.save(account);
        AccountDTO savedAccountDTO = new AccountDTO();
        BeanUtils.copyProperties(savedAccount, savedAccountDTO);
        return savedAccountDTO;
    }

    @Transactional(readOnly = true)
    public AccountDTO getAccountById(Integer accountId) throws AccountNotFoundException {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new AccountNotFoundException("Account " + accountId + " not found"));
        AccountDTO accountDTO = new AccountDTO();
        BeanUtils.copyProperties(account, accountDTO);
        return accountDTO;
    }

    public AccountDTO updateAccount(Integer accountId, UpdateAccountDTO updateAccountDTO)
            throws AccountNotFoundException, IllegalStateException {
        Account existingAccount = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account " + accountId + " not found"));
        validateActiveAccount(existingAccount);
        BeanUtils.copyProperties(updateAccountDTO, existingAccount);
        Account updatedAccount = accountRepository.save(existingAccount);
        AccountDTO updatedAccountDTO = new AccountDTO();
        BeanUtils.copyProperties(updatedAccount, updatedAccountDTO);
        return updatedAccountDTO;
    }

    public AccountDTO updateAccountBalance(Integer accountId, Double amount)
            throws AccountNotFoundException, IllegalStateException {
        Account existingAccount = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account " + accountId + " not found"));
        validateActiveAccount(existingAccount);
        double newBalance = existingAccount.getBalance() + amount;
        validateNewBalance(newBalance);
        existingAccount.setBalance(newBalance);
        Account updatedAccount = accountRepository.save(existingAccount);
        AccountDTO updatedAccountDTO = new AccountDTO();
        BeanUtils.copyProperties(updatedAccount, updatedAccountDTO);
        return updatedAccountDTO;
    }

    public AccountDTO updateAccountState(Integer accountId, AccountStateEnum state) throws AccountNotFoundException {
        Account existingAccount = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account " + accountId + " not found"));
        existingAccount.setAccountState(state);
        Account updatedAccount = accountRepository.save(existingAccount);
        AccountDTO updatedAccountDTO = new AccountDTO();
        BeanUtils.copyProperties(updatedAccount, updatedAccountDTO);
        return updatedAccountDTO;
    }

    public AccountDTO deleteAccount(Integer accountId) throws AccountNotFoundException {
        Account existingAccount = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account " + accountId + " not found"));
        accountRepository.delete(existingAccount);
        AccountDTO deletedAccountDTO = new AccountDTO();
        BeanUtils.copyProperties(existingAccount, deletedAccountDTO);
        return deletedAccountDTO;
    }

    private void validateNewBalance(double newBalance) {
        if (newBalance < 0) {
            throw new IllegalArgumentException("Account balance cannot be negative");
        }
    }

    private void validateActiveAccount(Account existingAccount) throws IllegalStateException {
        if (existingAccount.getAccountState() != AccountStateEnum.ACTIVE) {
            throw new IllegalStateException("Account must be ACTIVE to perform this operation");
        }
    }
}
