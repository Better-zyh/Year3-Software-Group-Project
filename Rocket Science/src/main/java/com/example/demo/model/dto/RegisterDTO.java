package com.example.demo.model.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


@Data
public class RegisterDTO {

    @NotEmpty(message = "Please inout name")
    @Length(min = 3, max = 10, message = "length between 3 to 10")
    private String username;

    @NotEmpty(message = "Please input password")
    @Length(min =3 , max = 10, message = "length between 3 to 10")
    private String password;

    @NotEmpty(message = " Password again")
    @Length(min = 3, max = 10, message = "length between 3 to 10")
    private String checkPass;

    @NotEmpty(message = "Input Email address")
    @Email(message = "Input the email with type of:xxx.xxx@student.xjtlu.edu.cn")
    private String email;
}
