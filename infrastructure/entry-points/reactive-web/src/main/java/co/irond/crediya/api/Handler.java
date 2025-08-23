package co.irond.crediya.api;

import co.irond.crediya.api.dto.UserRegistrationDto;
import co.irond.crediya.api.utils.UserMapper;
import co.irond.crediya.api.utils.ValidationService;
import co.irond.crediya.model.user.User;
import co.irond.crediya.usecase.user.UserUseCaseInterface;
import jakarta.validation.ValidationException;
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

    private final UserUseCaseInterface userUseCase;
    private final ValidationService validationService;
    private final UserMapper userMapper;

    public Mono<ServerResponse> listenGETUseCase(ServerRequest serverRequest) {
        return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(userUseCase.getAllUsers(), User.class);
    }

    public Mono<ServerResponse> listenSaveUser(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(UserRegistrationDto.class)
                .flatMap(validationService::validateObject)
                .map(userMapper::toUser)
                .flatMap(userUseCase::saveUser)
                .flatMap(savedUser -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(savedUser))
                .onErrorResume(ValidationException.class, ex ->
                        ServerResponse.badRequest()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue("{\"error\": \"" + ex.getMessage() + "\"}"));
    }

    public Mono<ServerResponse> createUser(ServerRequest request) {
        return request.bodyToMono(UserRegistrationDto.class)
                .flatMap(validationService::validateObject)
                .map(userMapper::toUser)
                .flatMap(userDto -> {
                   log.info("Usuario validado correctamente: " + userDto.getClass());

                    return ServerResponse.ok().bodyValue("Usuario creado exitosamente");
                })
                .onErrorResume(ValidationException.class, ex ->
                        ServerResponse.badRequest()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue("{\"error\": \"" + ex.getMessage() + "\"}"));
    }
}
