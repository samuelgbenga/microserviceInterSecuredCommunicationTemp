package ng.samuel.mloginregtemp.authenticationservice.dto;

public record LoginResponseDto(String responseCode, String responseMessage, String jwt) {
}
