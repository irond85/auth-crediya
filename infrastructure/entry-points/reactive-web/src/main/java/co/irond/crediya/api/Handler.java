package co.irond.crediya.api;

import co.irond.crediya.api.dto.ApiResponseDto;
import co.irond.crediya.api.dto.LoginRequestDto;
import co.irond.crediya.api.dto.UserRegistrationDto;
import co.irond.crediya.api.utils.LoginMapper;
import co.irond.crediya.api.utils.UserMapper;
import co.irond.crediya.api.utils.ValidationService;
import co.irond.crediya.constants.OperationsMessage;
import co.irond.crediya.model.exceptions.CrediYaException;
import co.irond.crediya.model.exceptions.ErrorCode;
import co.irond.crediya.model.user.User;
import co.irond.crediya.r2dbc.service.UserService;
import co.irond.crediya.usecase.login.LoginUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
@Slf4j
public class Handler {

    private final UserService userService;
    private final ValidationService validationService;
    private final UserMapper userMapper;
    private final LoginUseCase loginUseCase;
    private final LoginMapper loginMapper;

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
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ADVISOR')")
    public Mono<ServerResponse> listenGETUseCase(ServerRequest request) {
        return ok().contentType(MediaType.TEXT_EVENT_STREAM).body(userService.getAllUsers(), User.class);
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
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ADVISOR')")
    public Mono<ServerResponse> listenSaveUser(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(UserRegistrationDto.class)
                .doOnNext(userRegistrationDto -> log.info(OperationsMessage.REQUEST_RECEIVED.getMessage(), userRegistrationDto.toString()))
                .flatMap(validationService::validateObject)
                .map(userMapper::toUser)
                .flatMap(userService::saveUser)
                .flatMap(savedUser -> {
                    ApiResponseDto<Object> response = ApiResponseDto.builder()
                            .status("201")
                            .message(OperationsMessage.RESOURCE_CREATED.getMessage())
                            .data(savedUser).build();
                    return ServerResponse.status(201).contentType(MediaType.APPLICATION_JSON).bodyValue(response);
                });
    }

    @Operation(
            operationId = "getUserEmailByDni",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "get user email from user dni successfully.",
                            content = @Content(
                                    schema = @Schema(implementation = ApiResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "B_001",
                            description = "User with dni doesn't exists.",
                            content = @Content(
                                    schema = @Schema(implementation = ApiResponseDto.class)
                            )
                    )
            },
            parameters = {
                    @Parameter(
                            in = ParameterIn.PATH,
                            name = "dni",
                            required = true,
                            description = "The unique DNI of the user.",
                            example = "1"
                    )
            }
    )
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public Mono<ServerResponse> listenGetUserEmailByDni(ServerRequest request) {
        String dniUser = request.pathVariable("dni");
        return userService.getUserEmailByDni(dniUser)
                .doOnNext(dni -> log.info(OperationsMessage.REQUEST_RECEIVED.getMessage(), dni))
                .switchIfEmpty(Mono.error(new CrediYaException(ErrorCode.USER_NOT_FOUND)))
                .flatMap(userEmail -> {
                    ApiResponseDto<Object> response = ApiResponseDto.builder()
                            .status("Success")
                            .message(OperationsMessage.USER_EXISTS.getMessage())
                            .data(userEmail).build();
                    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(response);
                });
    }

    @Operation(
            operationId = "loginUser",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User logged successful",
                            content = @Content(
                                    schema = @Schema(implementation = ApiResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "No token was found in the request.",
                            content = @Content(
                                    schema = @Schema(implementation = ApiResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Credentials don't match.",
                            content = @Content(
                                    schema = @Schema(implementation = ApiResponseDto.class)
                            )
                    ),
            },
            requestBody = @RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = LoginRequestDto.class)
                    )
            )
    )
    public Mono<ServerResponse> listenLoginUser(ServerRequest request) {
        return request.bodyToMono(LoginRequestDto.class)
                .doOnNext(loginDto -> log.info(OperationsMessage.REQUEST_RECEIVED.getMessage(), loginDto.toString()))
                .flatMap(validationService::validateObject)
                .map(loginMapper::toLoginDto)
                .flatMap(loginUseCase::login)
                .flatMap(token -> {
                    ApiResponseDto<Object> response = ApiResponseDto.builder()
                            .status("200")
                            .message(OperationsMessage.LOGIN_OK.getMessage())
                            .data(token).build();
                    return ServerResponse.status(200).contentType(MediaType.APPLICATION_JSON).bodyValue(response);
                });
    }

}
