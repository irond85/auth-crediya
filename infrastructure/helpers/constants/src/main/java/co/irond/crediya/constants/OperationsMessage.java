package co.irond.crediya.constants;

import lombok.Getter;

@Getter
public enum OperationsMessage {
    REQUEST_RECEIVED("Request received for entity {}"),
    OPERATION_ERROR("Error failed service transactional: {}"),
    SAVE_OK("User with email {} saved successfully."),
    RESOURCE_CREATED("Resource created successful."),
    USER_EXISTS("User was finded");


    private final String message;

    OperationsMessage(String message) {
        this.message = message;
    }
}