package com.exa.userservice.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class CreateUserDTO implements Serializable {
    String firstName;
    String lastName;
    String email;
    String phone;
}
