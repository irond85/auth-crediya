package co.irond.crediya.r2dbc;

import co.irond.crediya.constants.OperationsMessage;
import co.irond.crediya.model.dto.LoginDto;
import co.irond.crediya.model.dto.TokenDto;
import co.irond.crediya.model.exceptions.CrediYaException;
import co.irond.crediya.model.exceptions.ErrorCode;
import co.irond.crediya.model.user.User;
import co.irond.crediya.model.user.gateways.UserRepository;
import co.irond.crediya.r2dbc.entity.UserEntity;
import co.irond.crediya.r2dbc.helper.ReactiveAdapterOperations;
import co.irond.crediya.security.jwt.JwtProvider;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public UserReactiveRepositoryAdapter(UserReactiveRepository repository, ObjectMapper mapper, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {

        super(repository, mapper, entity -> mapper.map(entity, User.class));
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public Mono<User> saveUser(User entity) {
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
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
    public Mono<User> findUserByDni(String dni) {
        return repository.findUserByDni(dni);
    }

    @Override
    public Mono<TokenDto> login(LoginDto loginDto) {
        return repository.findByEmail(loginDto.email())
                .doOnNext(request -> log.info(OperationsMessage.REQUEST_RECEIVED.getMessage(), loginDto.toString()))
                .filter(userEntity -> passwordEncoder.matches(loginDto.password(), userEntity.getPassword()))
                .switchIfEmpty(Mono.error(new CrediYaException(ErrorCode.BAD_CREDENTIALS)))
                .map(userEntity -> new TokenDto(jwtProvider.generateToken(userEntity)))
                .doOnError(error -> log.error(OperationsMessage.OPERATION_ERROR.getMessage(), error.toString()));
    }

    @Override
    public Mono<User> findUserByEmail(String email) {
        return repository.findUserByEmail(email);
    }
}
