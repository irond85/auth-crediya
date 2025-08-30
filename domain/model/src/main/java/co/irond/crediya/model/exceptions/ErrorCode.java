package co.irond.crediya.model.exceptions;

import lombok.Getter;

@Getter
public enum ErrorCode {
    EMAIL_ALREADY_EXISTS("BA_AE_001", "Already exists a user with this email.", 409),
    INVALID_BASE_SALARY("BA_NR_001", "The base salary is not within range.", 422),
    USER_NOT_FOUND("BA_NF_001", "The user with dni doesn't exists.", 404),
    DNI_ALREADY_EXISTS("BA_AE_002", "Already exists a user with this dni.", 409),
    ROLE_NOT_FOUND("BA_NF_002", "Role with id {} not found.", 404),
    BAD_TOKEN("BA_T_001", "No token has been sent in the request.", 403),
    NO_TOKEN("BA_T_002", "No token was found in the request.", 403),
    INVALID_TOKEN("BA_T_003", "Invalid authentication in the request.", 403),
    BAD_CREDENTIALS("BA_BC_001", "Credentials don't match", 401);

    private final String internCode;
    private final String message;
    private final int httpCode;

    ErrorCode(String internCode, String message, int httpCode) {
        this.internCode = internCode;
        this.message = message;
        this.httpCode = httpCode;
    }

}
