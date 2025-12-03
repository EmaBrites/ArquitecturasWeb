package com.exa.userservice.service;

import com.exa.userservice.enums.AccountTypeEnum;
import com.exa.userservice.dto.*;
import com.exa.userservice.entity.User;
import com.exa.userservice.enums.RoleEnum;
import com.exa.userservice.feignClients.AccountFeignClients;
import com.exa.userservice.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@Service
@Transactional

public class UserService {

    private final UserRepository userRepository;
    private final AccountFeignClients accountFeignClients;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AccountFeignClients accountFeignClients) {
        this.userRepository = userRepository;
        this.accountFeignClients = accountFeignClients;
    }

    public UserDTO createUser(CreateUserDTO dto) {
        User user = new User();
        BeanUtils.copyProperties(dto, user);
//        List<RoleEnum> roles = user.getRoles(); //TODO validar esto, en teória se debería manejar desde el gw los roles
//        roles.add(RoleEnum.CUSTOMER);
//        user.setRoles(roles);
        User saved = userRepository.save(user);
        UserDTO result = new UserDTO();
        BeanUtils.copyProperties(saved, result);
        return result;
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> {
            UserDTO dto = new UserDTO();
            BeanUtils.copyProperties(user, dto);
            return dto;
        }).toList();
    }

    @Transactional(readOnly = true)
    public UserDTO getUserById(Integer id) throws AccountNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("User " + id + " not found"));
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(user, dto);
        return dto;
    }

    public UserDTO updateUser(Integer id, UpdateUserDTO dto) throws AccountNotFoundException {
        User existing = userRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("User " + id + " not found"));
        BeanUtils.copyProperties(dto, existing);
        User updated = userRepository.save(existing);
        UserDTO result = new UserDTO();
        BeanUtils.copyProperties(updated, result);
        return result;
    }

    public boolean deleteUser(Integer id) throws AccountNotFoundException {
        User existing = userRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("User " + id + " not found"));
        userRepository.delete(existing);
        return true;
    }

    public void associateAccount(Integer userId, AccountTypeEnum accountType) throws AccountNotFoundException {
        userRepository.findById(userId).orElseThrow(() -> new AccountNotFoundException("User not found"));
        CreateAccountDTO newAccount = new CreateAccountDTO(userId, accountType);
        accountFeignClients.createAccount(newAccount);
    }

    public void disassociateAccount(Integer userId, Integer accountId) throws AccountNotFoundException {
        userRepository.findById(userId).orElseThrow(() -> new AccountNotFoundException("User not found"));
        try {
            ResponseEntity<AccountDTO> accountResponse = accountFeignClients.getAccountById(accountId);
            if (accountResponse.getStatusCode().is2xxSuccessful() && accountResponse.getBody() != null) {
                accountFeignClients.deleteAccount(accountId);
            } else {
                throw new AccountNotFoundException("Account not found");
            }
        } catch (Exception e) {
            throw new RuntimeException("Account not found or account-service unavailable");
        }
    }

    public Page<UserDTO> searchUsers(String email, String phone, String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = userRepository.searchUsers(email, phone, name, pageable);
        return users.map(user -> {
            UserDTO dto = new UserDTO();
            BeanUtils.copyProperties(user, dto);
            return dto;
        });
    }

    public UserDetailsDTO getUserByUsername(String username) throws AccountNotFoundException {
        UserDetailsDTO usersByEmail = userRepository.getUsersByEmail(username);
        if (usersByEmail == null) {
            throw new AccountNotFoundException("Username not found");
        }
        return usersByEmail;
    }
}
