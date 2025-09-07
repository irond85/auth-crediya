package co.irond.crediya.r2dbc.service;

import co.irond.crediya.constants.OperationsMessage;
import co.irond.crediya.model.user.User;
import co.irond.crediya.usecase.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserUseCase userUseCase;
    private final TransactionalOperator transactionalOperator;

    public Mono<User> saveUser(User user) {
        return transactionalOperator.execute(transaction ->
                        userUseCase.saveUser(user)
                )
                .doOnNext(savedUser -> log.info(OperationsMessage.SAVE_OK.getMessage(), savedUser.getEmail()))
                .doOnError(throwable -> log.error(OperationsMessage.OPERATION_ERROR.getMessage(), "SaveUser. "
                        + throwable.getMessage()))
                .single();
    }

    public Flux<User> getAllUsers() {
        return userUseCase.getAllUsers();
    }

    public Mono<User> getUserByDni(String dni) {
        return userUseCase.getUserById(dni);
    }

    public Mono<User> getUserByEmail(String email) {
        return userUseCase.getUserByEmail(email);
    }
}
