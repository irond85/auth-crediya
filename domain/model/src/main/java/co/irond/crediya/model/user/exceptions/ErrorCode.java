package co.irond.crediya.model.user.exceptions;

import lombok.Getter;

@Getter
public enum ErrorCode {
    EMAIL_ALREADY_EXISTS("BA_AE_001", "Already exists a user with this email.", 409),
    INVALID_BASE_SALARY("BA_NR_001", "The base salary is not within range.", 422),
    USER_NOT_FOUND("BA_NF_001", "The user with dni doesn't exists.", 404),
    DNI_ALREADY_EXISTS("BA_AE_002", "Already exists a user with this dni.", 409);

    private final String internCode;
    private final String message;
    private final int httpCode;

    ErrorCode(String internCode, String message, int httpCode) {
        this.internCode = internCode;
        this.message = message;
        this.httpCode = httpCode;
    }

}
