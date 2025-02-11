package ng.samuel.mloginregtemp.authenticationservice.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ApplicationProperties {

    @Value("${application.security.jwt.secret}")
    private String jwtSecret;

}
