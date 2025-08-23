package co.irond.crediya.usecase.user;

import co.irond.crediya.model.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserUseCaseInterface {

    Mono<User> saveUser(User user);

    Flux<User> getAllUsers();
}
