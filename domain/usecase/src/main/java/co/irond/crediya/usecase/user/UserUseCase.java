package co.irond.crediya.usecase.user;

import co.irond.crediya.model.user.User;
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
            throw new IllegalArgumentException("The Base Salary not within range");
        }


        return userRepository.existsByEmail(user.getEmail())
                .filter(exists -> !exists)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Email already exists")))
                .then(userRepository.saveUser(user));
    }

    public Flux<User> getAllUsers() {
        return userRepository.findAllUsers();
    }

}
