package com.exa.userservice.dto;

import lombok.Value;
import java.io.Serializable;

@Value
public class CreateUserDTO implements Serializable {
    String firstName;
    String lastName;
    String email;
    String password;
    String phone;
}
