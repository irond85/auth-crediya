package co.irond.crediya.model.user.gateways;

import co.irond.crediya.model.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {

    Mono<User> saveUser(User user);

    Flux<User> findAllUsers();

    Mono<Boolean> existsByEmail(String email);

}
