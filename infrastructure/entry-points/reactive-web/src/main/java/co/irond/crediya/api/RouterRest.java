package co.irond.crediya.api;

import co.irond.crediya.api.config.AuthPath;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class RouterRest {
    private final AuthPath authPath;

    @Bean
    @RouterOperations({
            @RouterOperation(path = "/api/v1/usuarios", method = RequestMethod.GET, beanClass = Handler.class, beanMethod = "listenGETUseCase"),
            @RouterOperation(path = "/api/v1/usuarios", method = RequestMethod.POST, beanClass = Handler.class, beanMethod = "listenSaveUser"),
            @RouterOperation(path = "/api/v1/usuarios/dni/{dni}", method = RequestMethod.GET, beanClass = Handler.class, beanMethod = "listenGetUserEmailByDni")

    })
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(GET(authPath.getUsuarios()), handler::listenGETUseCase)
                .andRoute(POST(authPath.getUsuarios()), handler::listenSaveUser)
                .andRoute(GET(authPath.getUsuarioByDni()), handler::listenGetUserEmailByDni);
    }
}
