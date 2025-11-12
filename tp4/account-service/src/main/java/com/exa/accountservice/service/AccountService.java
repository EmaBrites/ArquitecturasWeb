package com.exa.accountservice.service;

import com.exa.accountservice.dto.AccountDTO;
import com.exa.accountservice.dto.CreateAccountDTO;
import com.exa.accountservice.dto.UpdateAccountDTO;
import com.exa.accountservice.entity.Account;
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

    public AccountDTO createAccount(CreateAccountDTO dto) {
        Account account = new Account();
        account.setUserId(dto.getUserId());
        account.setAccountType(dto.getAccountType());
        account.setBalance(dto.getBalance());
        account.setCreatedDate(LocalDateTime.now());

        Account saved = accountRepository.save(account);

        AccountDTO response = new AccountDTO();
        response.setId(saved.getId());
        response.setUserId(saved.getUserId());
        response.setAccountType(saved.getAccountType());
        response.setBalance(saved.getBalance());
        response.setCreatedDate(saved.getCreatedDate());

        return response;
    }


    @Transactional(readOnly = true)
    public AccountDTO getAccountById(Integer accountId) throws AccountNotFoundException {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new AccountNotFoundException("Account " + accountId + " not found"));
        AccountDTO accountDTO = new AccountDTO();
        BeanUtils.copyProperties(account, accountDTO);
        return accountDTO;
    }

    public AccountDTO updateAccount(int accounId, UpdateAccountDTO updateAccountDTO) throws AccountNotFoundException {
        Account existingAccount = accountRepository.findById(accounId)
                .orElseThrow(() -> new AccountNotFoundException("Account " + accounId + " not found"));
        BeanUtils.copyProperties(updateAccountDTO, existingAccount);
        Account updatedAccount = accountRepository.save(existingAccount);
        AccountDTO updatedAccountDTO = new AccountDTO();
        BeanUtils.copyProperties(updatedAccount, updatedAccountDTO);
        return updatedAccountDTO;
    }

    public void updateAccountBalance(Integer accountId, Double balance) throws AccountNotFoundException {
        Account existingAccount = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account " + accountId + " not found"));
        existingAccount.setBalance(existingAccount.getBalance() + balance);
        accountRepository.save(existingAccount);
    }

    public boolean deleteAccount(Integer accountId) throws AccountNotFoundException {
        Account existingAccount = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account " + accountId + " not found"));
        accountRepository.delete(existingAccount);
        return true;
    }
}
