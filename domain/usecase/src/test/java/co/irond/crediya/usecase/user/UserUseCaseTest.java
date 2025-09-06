package co.irond.crediya.usecase.user;

import co.irond.crediya.model.exceptions.CrediYaException;
import co.irond.crediya.model.user.User;
import co.irond.crediya.model.user.gateways.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @InjectMocks
    UserUseCase userUseCase;

    @Mock
    UserRepository userRepository;

    private User user;
    private final String email = "myEmail@mail.com";

    @BeforeEach
    void initMocks() {
        user = new User();
        user.setName("Sergio");
        user.setLastName("Agudelo");
        user.setBirthday(LocalDateTime.of(2025, 8, 25, 20, 0));
        user.setEmail(email);
        user.setAddress("My Address 123");
        user.setBaseSalary(BigDecimal.TEN);
        user.setDni("1234");
    }

    @Test
    void saveUser() {
        when(userRepository.saveUser(any(User.class))).thenReturn(Mono.just(user));
        when(userRepository.existsByEmail(anyString())).thenReturn(Mono.just(false));
        when(userRepository.findUserByDni(anyString())).thenReturn(Mono.empty());

        Mono<User> response = userUseCase.saveUser(user);

        StepVerifier.create(response)
                .expectNextMatches(value -> value.equals(user))
                .verifyComplete();

        verify(userRepository, times(1)).saveUser(any(User.class));
        verify(userRepository, times(1)).existsByEmail(anyString());
        verify(userRepository, times(1)).findUserByDni(anyString());
    }

    @Test
    void whenUserAlreadyExist_shouldReturnException() {
        when(userRepository.existsByEmail(anyString())).thenReturn(Mono.just(true));
        when(userRepository.findUserByDni(anyString())).thenReturn(Mono.just(user));

        Executable executable = () -> userUseCase.saveUser(user).block();

        CrediYaException exception = assertThrows(CrediYaException.class, executable);
        assertEquals("Already exists a user with this email.", exception.getMessage());

        verify(userRepository, times(1)).existsByEmail(anyString());
        verify(userRepository, never()).saveUser(any(User.class));
    }

    @Test
    void whenBaseSalaryIsLessThanZero_shouldReturnException() {
        user.setBaseSalary(new BigDecimal("-500"));

        assertThrows(CrediYaException.class, () -> userUseCase.saveUser(user));

        verify(userRepository, never()).saveUser(any(User.class));
        verify(userRepository, never()).existsByEmail(anyString());
    }

    @Test
    void whenBaseSalaryIsGreaterThanZero_shouldReturnException() {
        user.setBaseSalary(new BigDecimal("15000001"));

        assertThrows(CrediYaException.class, () -> userUseCase.saveUser(user));

        verify(userRepository, never()).saveUser(any(User.class));
        verify(userRepository, never()).existsByEmail(anyString());
    }

    @Test
    void getAllUsers() {
        when(userRepository.findAllUsers()).thenReturn(Flux.just(user));

        Flux<User> response = userUseCase.getAllUsers();

        StepVerifier.create(response)
                .expectNextMatches(value -> value.equals(user))
                .verifyComplete();

        verify(userRepository, times(1)).findAllUsers();
    }

    @Test
    void getUserEmailById() {
        when(userRepository.findUserByDni(anyString())).thenReturn(Mono.just(user));

        Mono<User> response = userUseCase.getUserById(email);

        StepVerifier.create(response)
                .expectNextMatches(value -> value.equals(user))
                .verifyComplete();

        verify(userRepository, times(1)).findUserByDni(anyString());
    }
}