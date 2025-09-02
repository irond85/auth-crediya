package co.irond.crediya.r2dbc.helper;

import co.irond.crediya.model.exceptions.CrediYaException;
import co.irond.crediya.model.exceptions.ErrorCode;
import lombok.Getter;

@Getter
public enum UserRoles {
    ADMIN(1L, "ADMIN"),
    ADVISOR(2L, "ADVISOR"),
    CUSTOMER(3L, "CUSTOMER");

    private final Long id;
    private final String nameRole;

    UserRoles(Long id, String nameRole) {
        this.id = id;
        this.nameRole = nameRole;
    }

    public static UserRoles fromId(Long id) {
        for (UserRoles role : values()) {
            if (role.getId().equals(id)) {
                return role;
            }
        }
        throw new CrediYaException(ErrorCode.ROLE_NOT_FOUND);
    }
}
