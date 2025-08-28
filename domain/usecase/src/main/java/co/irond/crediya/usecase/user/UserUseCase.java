package co.irond.crediya.usecase.user;

import co.irond.crediya.model.user.User;
import co.irond.crediya.model.user.exceptions.CrediYaException;
import co.irond.crediya.model.user.exceptions.ErrorCode;
import co.irond.crediya.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class UserUseCase {

    private final UserRepository userRepository;
    private static final BigDecimal salaryMax = new BigDecimal(15000000);

    public Mono<User> saveUser(User user) {
        if (user.getBaseSalary().compareTo(BigDecimal.ZERO) < 0 || user.getBaseSalary().compareTo(salaryMax) > 0) {
            throw new CrediYaException(ErrorCode.INVALID_BASE_SALARY);
        }

        Mono<Boolean> emailExists = userRepository.existsByEmail(user.getEmail());
        Mono<Boolean> dniExists = userRepository.findEmailByDni(user.getDni())
                .hasElement();

        return Mono.zip(emailExists, dniExists)
                .flatMap(result -> {
                    boolean emailAlreadyExists = result.getT1();
                    boolean dniAlreadyExists = result.getT2();

                    if (emailAlreadyExists) {
                        return Mono.error(new CrediYaException(ErrorCode.EMAIL_ALREADY_EXISTS));
                    }
                    if (dniAlreadyExists) {
                        return Mono.error(new CrediYaException(ErrorCode.DNI_ALREADY_EXISTS));
                    }
                    return userRepository.saveUser(user);
                });
    }

    public Flux<User> getAllUsers() {
        return userRepository.findAllUsers();
    }

    public Mono<String> getUserEmailById(String dni) {
        return userRepository.findEmailByDni(dni);
    }

}
