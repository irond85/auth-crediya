package co.irond.crediya.r2dbc.service;

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
                .doOnNext(savedUser -> log.info("User {} saved successfully.", savedUser.getName()))
                .doOnError(throwable -> log.error("Error failed service transactional: SaveUser. {}", throwable.getMessage()))
                .single();
    }

    public Flux<User> getAllUsers() {
        return userUseCase.getAllUsers();
    }

}
