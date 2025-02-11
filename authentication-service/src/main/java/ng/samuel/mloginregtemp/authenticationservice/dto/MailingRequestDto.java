package ng.samuel.mloginregtemp.authenticationservice.dto;

public record MailingRequestDto (String to, String subject, String fullName, String event) {
}
