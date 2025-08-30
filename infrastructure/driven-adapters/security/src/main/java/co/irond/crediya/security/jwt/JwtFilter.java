package co.irond.crediya.security.jwt;

import co.irond.crediya.model.exceptions.CrediYaException;
import co.irond.crediya.model.exceptions.ErrorCode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class JwtFilter implements WebFilter {


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest serverRequest = exchange.getRequest();
        String path = serverRequest.getPath().value();

        if (path.contains("/login") || path.contains("swagger-ui/"))
            return chain.filter(exchange);

        String auth = serverRequest.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (auth == null)
            return Mono.error(new CrediYaException(ErrorCode.NO_TOKEN));
        if (!auth.startsWith("Bearer "))
            return Mono.error(new CrediYaException(ErrorCode.INVALID_TOKEN));

        String token = auth.replace("Bearer ", "");
        exchange.getAttributes().put("token", token);

        return chain.filter(exchange);
    }
}
