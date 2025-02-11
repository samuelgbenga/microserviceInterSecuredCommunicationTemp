package ng.samuel.mloginregtemp.authenticationservice.service;

import ng.samuel.mloginregtemp.authenticationservice.dto.MailingRequestDto;
import ng.samuel.mloginregtemp.authenticationservice.dto.MailingResponseDto;

public interface SecuredMailService {

    MailingResponseDto sendSecretMail(MailingRequestDto dto);
}
