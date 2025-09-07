package co.irond.crediya.r2dbc;

import co.irond.crediya.model.user.User;
import co.irond.crediya.r2dbc.entity.UserEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserReactiveRepository extends ReactiveCrudRepository<UserEntity, Long>, ReactiveQueryByExampleExecutor<UserEntity> {

    Mono<Boolean> existsByEmail(String email);

    @Query("SELECT * FROM users WHERE dni = :dni")
    Mono<User> findUserByDni(String dni);

    Mono<UserEntity> findByEmail(String email);

    Mono<User> findUserByEmail(String email);

}
