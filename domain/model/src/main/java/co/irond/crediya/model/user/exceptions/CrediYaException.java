package co.irond.crediya.model.user.exceptions;

import lombok.Getter;

@Getter
public class CrediYaException extends RuntimeException {

    private final ErrorCode errorCode;

    public CrediYaException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
