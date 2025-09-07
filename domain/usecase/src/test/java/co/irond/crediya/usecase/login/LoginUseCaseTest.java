package co.irond.crediya.usecase.login;

import co.irond.crediya.model.dto.LoginDto;
import co.irond.crediya.model.dto.TokenDto;
import co.irond.crediya.model.user.gateways.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginUseCaseTest {

    @InjectMocks
    private LoginUseCase loginUseCase;

    @Mock
    private UserRepository userRepository;

    private LoginDto loginDto;
    private TokenDto tokenDto;

    @BeforeEach
    void initMocks() {
        loginDto = new LoginDto("myEmail@email.com", "admin");
        tokenDto = new TokenDto("Bearer myToken");
    }

    @Test
    void loginUser() {
        when(userRepository.login(any(LoginDto.class))).thenReturn(Mono.just(tokenDto));

        Mono<TokenDto> response = loginUseCase.login(loginDto);

        StepVerifier.create(response)
                .expectNextMatches(value -> value.equals(tokenDto))
                .verifyComplete();

        verify(userRepository, times(1)).login(any(LoginDto.class));
    }
}
