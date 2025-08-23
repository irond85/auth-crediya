package co.irond.crediya.usecase.user;

import co.irond.crediya.model.user.User;
import co.irond.crediya.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserUseCase implements UserUseCaseInterface {

    private final UserRepository userRepository;

    @Override
    public Mono<User> saveUser(User user) {
        if (!ValidatorUtil.validateNullOrEmpty(user.getName()) || !ValidatorUtil.validateNullOrEmpty(user.getLastName()) || !ValidatorUtil.validateEmail(user.getEmail()) || !ValidatorUtil.validateMoney(user.getBaseSalary())) {
            throw new IllegalArgumentException("Error incorrect info");
        }

        return userRepository.save(user);
    }

    @Override
    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

}
