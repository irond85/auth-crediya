package co.irond.crediya.api;

import co.irond.crediya.api.dto.ApiResponse;
import co.irond.crediya.api.dto.UserRegistrationDto;
import co.irond.crediya.api.utils.UserMapper;
import co.irond.crediya.api.utils.ValidationService;
import co.irond.crediya.model.user.User;
import co.irond.crediya.r2dbc.service.UserService;
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

    public Mono<ServerResponse> listenGETUseCase(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(userService.getAllUsers(), User.class);
    }

    public Mono<ServerResponse> listenSaveUser(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(UserRegistrationDto.class)
                .flatMap(validationService::validateObject)
                .map(userMapper::toUser)
                .flatMap(userService::saveUser)
                .flatMap(savedUser -> {
                    ApiResponse<Object> response = ApiResponse.builder()
                            .status(201)
                            .message("User created successfull")
                            .path(serverRequest.path())
                            .data(savedUser).build();
                    return ServerResponse.status(201).contentType(MediaType.APPLICATION_JSON).bodyValue(response);
                });
    }

}
