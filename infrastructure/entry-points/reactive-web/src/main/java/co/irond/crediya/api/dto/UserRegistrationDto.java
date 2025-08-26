package co.irond.crediya.api.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDto {

    @NotEmpty(message = "name can't be empty")
    private String name;
    @NotEmpty(message = "lastName can't be empty")
    private String lastName;
    private Date birthday;
    private String address;
    private String phone;
    @NotEmpty(message = "email can't be empty")
    @Email
    private String email;
    @NotNull(message = "baseSalary can't be null")
    private BigDecimal baseSalary;

}