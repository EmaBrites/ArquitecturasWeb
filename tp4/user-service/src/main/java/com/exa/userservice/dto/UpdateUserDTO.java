package com.exa.userservice.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class UpdateUserDTO implements Serializable {
    String firstName;
    String lastName;
    String phone;
}
