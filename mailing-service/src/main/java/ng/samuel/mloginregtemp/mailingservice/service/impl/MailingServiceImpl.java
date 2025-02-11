package ng.samuel.mloginregtemp.mailingservice.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import ng.samuel.mloginregtemp.mailingservice.config.ApplicationProperties;
import ng.samuel.mloginregtemp.mailingservice.dto.MailingRequestDTO;
import ng.samuel.mloginregtemp.mailingservice.service.MailingService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;

import org.thymeleaf.context.Context;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class MailingServiceImpl implements MailingService {

    private static final String MAILING_TEMPLATE = "mailing.html";

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final ApplicationProperties applicationProperties;

    // fix the properties in the context
    @Override
    public void send(MailingRequestDTO dto) throws MessagingException {

        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        Context context = new Context();
        Map<String, Object> variables = Map.of(
                "name", dto.name(),
                "event", dto.event()
        );
        context.setVariables(variables);

        sendMail(dto.to(), dto.subject(), msg, helper, variables );
    }

    @Override
    public void sendSecret(MailingRequestDTO dto) throws MessagingException {

        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        Context context = new Context();
        Map<String, Object> variables = Map.of(
                "name", dto.name(),
                "event", dto.event() + "Secrete for " + getCurrentUser().get()
        );
        context.setVariables(variables);

        sendMail(dto.to(), dto.subject(), msg, helper, variables );

    }


    // sends the email
    private void sendMail(String to, String subject, MimeMessage mimeMessage, MimeMessageHelper helper, Map<String, Object> properties) throws MessagingException {
        Context context = new Context();
        context.setVariables(properties);

        helper.setFrom(applicationProperties.getSender());
        helper.setTo(to);
        helper.setSubject(subject);

        String template = templateEngine.process(MAILING_TEMPLATE, context);
        helper.setText(template, true);
        mailSender.send(mimeMessage);
    }


    private Optional<String> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }
        Object principal = authentication.getPrincipal();
        if(principal instanceof UserDetails userDetails) {
            return Optional.of(userDetails.getUsername());
        }
        return Optional.of(principal.toString());
    }
}
