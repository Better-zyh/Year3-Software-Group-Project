package com.example.demo.model.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;

@Data
public class UpdateDTO {
    @Length(min = 3, max = 10, message = "length between 3 to 10")
    private String alias;

    @Length(min =3 , max = 10, message = "length between 3 to 10")
    private String old_password;

    @Length(min = 3, max = 10, message = "length between 3 to 10")
    private String new_password;

    private String avatar;
}
