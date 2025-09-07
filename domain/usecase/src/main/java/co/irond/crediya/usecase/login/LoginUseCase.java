package co.irond.crediya.usecase.login;

import co.irond.crediya.model.dto.LoginDto;
import co.irond.crediya.model.dto.TokenDto;
import co.irond.crediya.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class LoginUseCase {

    private final UserRepository userRepository;

    public Mono<TokenDto> login(LoginDto loginDto) {
        return userRepository.login(loginDto);
    }
}
