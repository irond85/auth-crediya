package co.irond.crediya.r2dbc.entity;

import co.irond.crediya.r2dbc.helper.UserRoles;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Stream;

@Table("users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserEntity implements UserDetails {

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
    @Column("rol_id")
    private Long role;
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String roleName = mapRoleIdToRoleName(role);
        return Stream.of(roleName).map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    private String mapRoleIdToRoleName(Long roleId) {
        return UserRoles.fromId(roleId).getNameRole();
    }
}
