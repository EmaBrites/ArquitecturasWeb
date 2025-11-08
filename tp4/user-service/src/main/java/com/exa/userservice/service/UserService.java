package com.exa.userservice.service;

import com.exa.userservice.dto.*;
import com.exa.userservice.entity.User;
import com.exa.userservice.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import java.time.LocalDateTime;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO createUser(CreateUserDTO dto) {
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        user.setCreatedAt((LocalDateTime.now()));
        User saved = userRepository.save(user);
        UserDTO result = new UserDTO();
        BeanUtils.copyProperties(saved, result);
        return result;
    }

    @Transactional(readOnly = true)
    public UserDTO getUserById(Integer id) throws AccountNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("User " + id + " not found"));
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(user, dto);
        return dto;
    }

    public UserDTO updateUser(Integer id, UpdateUserDTO dto) throws AccountNotFoundException {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("User " + id + " not found"));
        BeanUtils.copyProperties(dto, existing);
        User updated = userRepository.save(existing);
        UserDTO result = new UserDTO();
        BeanUtils.copyProperties(updated, result);
        return result;
    }

    public boolean deleteUser(Integer id) throws AccountNotFoundException {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("User " + id + " not found"));
        userRepository.delete(existing);
        return true;
    }
}
