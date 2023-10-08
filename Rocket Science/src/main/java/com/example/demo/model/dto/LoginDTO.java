package com.example.demo.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class LoginDTO {
    @NotBlank(message = "Account can not be empty")
    @Size(min = 2, max = 15, message = "Account length between 2-15")
    private String username;

    @NotBlank(message = "Password can not be empty")
    @Size(min = 6, max = 20, message = "Password between 6-20")
    private String password;

    private Boolean rememberMe;
}
