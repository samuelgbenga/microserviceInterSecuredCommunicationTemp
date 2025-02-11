package ng.samuel.mloginregtemp.mailingservice.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ApplicationProperties {

    @Value("${application.mail.sender}")
    private String sender;

    @Value("${application.security.jwt.secret}")
    private String jwtSecret;

}
