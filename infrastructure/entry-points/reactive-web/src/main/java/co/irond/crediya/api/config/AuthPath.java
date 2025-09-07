package co.irond.crediya.api.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "routes.paths")
public class AuthPath {
    private String usuarios;
    private String usuarioByDni;
    private String login;
    private String v1;
    private String usuarioByEmail;
}
