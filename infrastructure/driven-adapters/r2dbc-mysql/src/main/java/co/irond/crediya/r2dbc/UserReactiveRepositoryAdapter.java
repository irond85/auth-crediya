package co.irond.crediya.r2dbc;

import co.irond.crediya.model.user.User;
import co.irond.crediya.model.user.gateways.UserRepository;
import co.irond.crediya.r2dbc.entity.UserEntity;
import co.irond.crediya.r2dbc.helper.ReactiveAdapterOperations;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@Slf4j
public class UserReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        User,
        UserEntity,
        Long,
        UserReactiveRepository
        > implements UserRepository {
    public UserReactiveRepositoryAdapter(UserReactiveRepository repository, ObjectMapper mapper) {

        super(repository, mapper, entity -> mapper.map(entity, User.class));
    }

    @Override
    public Mono<User> saveUser(User entity) {
        return save(entity);
    }

    @Override
    public Flux<User> findAllUsers() {
        return findAll();
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public Mono<String> findEmailByDni(String dni) {
        return repository.findEmailByDni(dni);
    }
}
