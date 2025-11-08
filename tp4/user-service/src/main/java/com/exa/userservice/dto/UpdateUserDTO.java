package com.exa.userservice.dto;

import lombok.Value;
import java.io.Serializable;

@Value
public class UpdateUserDTO implements Serializable {
    String firstName;
    String lastName;
    String phone;
}
