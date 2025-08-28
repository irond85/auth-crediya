package co.irond.crediya.r2dbc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserEntity {

    @Id
    @Column("user_id")
    private Long id;
    private String name;
    @Column("last_name")
    private String lastName;
    private LocalDateTime birthday;
    private String address;
    private String phone;
    private String email;
    @Column("base_salary")
    private BigDecimal baseSalary;
    private String dni;

}
