package ng.samuel.mloginregtemp.authenticationservice.service.impl;

import lombok.RequiredArgsConstructor;
import ng.samuel.mloginregtemp.authenticationservice.dto.MailingRequestDto;
import ng.samuel.mloginregtemp.authenticationservice.dto.MailingResponseDto;
import ng.samuel.mloginregtemp.authenticationservice.service.SecuredMailService;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SecuredMailServiceImpl implements SecuredMailService {


    @Override
    public MailingResponseDto sendSecretMail(MailingRequestDto dto) {


        return new MailingResponseDto("003", String.format("Secured mail has been sent to %s", dto.to()));
    }
}
