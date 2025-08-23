package co.irond.crediya.api.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDto {

    @NotEmpty(message = "Name can't be empty")
    private String name;
    @NotEmpty(message = "LastName can't be empty")
    private String lastName;
    private LocalDateTime birthday;
    private String address;
    private String phone;
    @NotEmpty(message = "Email can't be empty")
    private String email;
    @NotNull(message = "baseSalary can't be null")
    @DecimalMin(value = "0.0", inclusive = true)
    @DecimalMax(value = "15000000.0", inclusive = true)
    private BigDecimal baseSalary;

}