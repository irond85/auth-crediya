package co.irond.crediya.api;

import co.irond.crediya.api.dto.UserRegistrationDto;
import co.irond.crediya.model.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {
    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/api/v1/usuarios",
                    produces = {MediaType.APPLICATION_JSON_VALUE},
                    method = RequestMethod.GET,
                    beanClass = Handler.class,
                    beanMethod = "listenGETUseCase",
                    operation = @Operation(
                            operationId = "getAllUsers",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "get all users successfully.",
                                            content = @Content(
                                                    schema = @Schema(implementation = User.class)
                                            )
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/v1/usuarios",
                    produces = {MediaType.APPLICATION_JSON_VALUE},
                    method = RequestMethod.POST,
                    beanClass = Handler.class,
                    beanMethod = "listenSaveUser",
                    operation = @Operation(
                            operationId = "createUser",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "201",
                                            description = "successful operation",
                                            content = @Content(
                                                    schema = @Schema(implementation = co.irond.crediya.api.dto.ApiResponse.class)
                                            )
                                    ),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "Fields empty or null",
                                            content = @Content(
                                                    schema = @Schema(implementation = co.irond.crediya.api.dto.ApiResponse.class)
                                            )
                                    ),
                            },
                            requestBody = @RequestBody(
                                    content = @Content(
                                            schema = @Schema(implementation = UserRegistrationDto.class)
                                    )
                            )
                    )
            )
    })
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(GET("/api/v1/usuarios"), handler::listenGETUseCase)
                .andRoute(POST("/api/v1/usuarios"), handler::listenSaveUser);
    }
}
