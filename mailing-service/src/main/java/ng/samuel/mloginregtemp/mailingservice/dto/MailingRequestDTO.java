package ng.samuel.mloginregtemp.mailingservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record MailingRequestDTO(
        @NotBlank(message = "field 'to' is mandatory : it can not be blank")
        @Email(message = "field 'to' must be a well formated email")
        String to,

        @NotBlank(message = "field 'subject' is mandatory : it can not be blank")
        String subject,

        @NotBlank(message = "field 'name' is mandatory : it can not be blank")
        String name,

        @NotBlank(message = "field 'event' is mandatory : it can not be blank")
        String event)
        {
        }
