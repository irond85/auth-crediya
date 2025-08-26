package co.irond.crediya.api;

import co.irond.crediya.api.dto.ApiResponseDto;
import co.irond.crediya.api.dto.UserRegistrationDto;
import co.irond.crediya.api.utils.UserMapper;
import co.irond.crediya.api.utils.ValidationService;
import co.irond.crediya.model.user.User;
import co.irond.crediya.r2dbc.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class Handler {

    private final UserService userService;
    private final ValidationService validationService;
    private final UserMapper userMapper;

    @Operation(
            operationId = "getAllUsers",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "get all users successfully.",
                            content = @Content(
                                    schema = @Schema(implementation = User.class)
                            )
                    )
            }
    )
    public Mono<ServerResponse> listenGETUseCase(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(userService.getAllUsers(), User.class);
    }

    @Operation(
            operationId = "createUser",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "successful operation",
                            content = @Content(
                                    schema = @Schema(implementation = ApiResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Fields empty or null",
                            content = @Content(
                                    schema = @Schema(implementation = ApiResponseDto.class)
                            )
                    ),
            },
            requestBody = @RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = UserRegistrationDto.class)
                    )
            )
    )
    public Mono<ServerResponse> listenSaveUser(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(UserRegistrationDto.class)
                .flatMap(validationService::validateObject)
                .map(userMapper::toUser)
                .flatMap(userService::saveUser)
                .flatMap(savedUser -> {
                    ApiResponseDto<Object> response = ApiResponseDto.builder()
                            .status(201)
                            .message("User created successfull")
                            .data(savedUser).build();
                    return ServerResponse.status(201).contentType(MediaType.APPLICATION_JSON).bodyValue(response);
                });
    }

}
