package co.irond.crediya.r2dbc;

import co.irond.crediya.model.dto.LoginDto;
import co.irond.crediya.model.dto.TokenDto;
import co.irond.crediya.model.user.User;
import co.irond.crediya.r2dbc.entity.UserEntity;
import co.irond.crediya.security.jwt.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserReactiveRepositoryAdapterTest {

    @InjectMocks
    UserReactiveRepositoryAdapter repositoryAdapter;

    @Mock
    UserReactiveRepository repository;

    @Mock
    ObjectMapper mapper;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    JwtProvider jwtProvider;

    private User user;
    private UserEntity userEntity;
    private LoginDto loginDto;
    private TokenDto tokenDto;

    @BeforeEach
    void initMocks() {
        userEntity = new UserEntity();
        userEntity.setName("Sergio");
        userEntity.setLastName("Agudelo");
        userEntity.setEmail("correo@correo.com");
        userEntity.setAddress("My Address 123");
        userEntity.setBaseSalary(BigDecimal.TEN);
        userEntity.setPassword("myPwd123");

        user = new User();
        user.setName("Sergio");
        user.setLastName("Agudelo");
        user.setEmail("correo@correo.com");
        user.setAddress("My Address 123");
        user.setBaseSalary(BigDecimal.TEN);
        user.setPassword("myPwd123");

        loginDto = new LoginDto("juan.ceballos@correo.com", "123");
        tokenDto = new TokenDto("eyJh123");
    }

    @Test
    void mustFindValueById() {
        when(repository.findById(anyLong())).thenReturn(Mono.just(userEntity));
        when(mapper.map(userEntity, User.class)).thenReturn(user);

        Long id = 1L;
        Mono<User> result = repositoryAdapter.findById(id);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.equals(user))
                .verifyComplete();
    }

    @Test
    void mustFindAllValues() {
        when(repository.findAll()).thenReturn(Flux.just(userEntity));
        when(mapper.map(userEntity, User.class)).thenReturn(user);

        Flux<User> result = repositoryAdapter.findAll();

        StepVerifier.create(result)
                .expectNextMatches(value -> value.equals(user))
                .verifyComplete();
    }

    @Test
    void mustExistsByEmail() {
        String email = user.getEmail();

        when(repository.existsByEmail(anyString())).thenReturn(Mono.just(true));

        Mono<Boolean> result = repositoryAdapter.existsByEmail(email);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.equals(true))
                .verifyComplete();
    }

    @Test
    void mustSaveValue() {
        when(mapper.map(any(User.class), eq(UserEntity.class))).thenReturn(userEntity);
        when(repository.save(any(UserEntity.class))).thenReturn(Mono.just(userEntity));
        when(mapper.map(userEntity, User.class)).thenReturn(user);

        Mono<User> result = repositoryAdapter.save(user);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.equals(user))
                .verifyComplete();
    }

    @Test
    void login() {
        when(repository.findByEmail(anyString())).thenReturn(Mono.just(userEntity));
        when(passwordEncoder.matches(loginDto.password(), userEntity.getPassword())).thenReturn(true);
        when(jwtProvider.generateToken(any(UserDetails.class))).thenReturn("eyJh123");

        Mono<TokenDto> result = repositoryAdapter.login(loginDto);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.equals(tokenDto))
                .verifyComplete();
    }
}
