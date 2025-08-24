package co.irond.crediya.r2dbc.entity;

import jakarta.persistence.*;
import lombok.*;
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
    @Column(name = "user_id")
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, name = "last_name")
    private String lastName;
    private LocalDateTime birthday;
    private String address;
    private String phone;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false, name = "base_salary")
    private BigDecimal baseSalary;

}
