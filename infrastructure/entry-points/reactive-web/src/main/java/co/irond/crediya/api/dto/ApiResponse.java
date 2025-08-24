package co.irond.crediya.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApiResponse<T> {

    private Integer status;
    private String message;
    private String path;
    private T data;
}
