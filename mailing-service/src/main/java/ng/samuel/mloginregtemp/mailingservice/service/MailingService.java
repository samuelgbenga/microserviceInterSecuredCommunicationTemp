package ng.samuel.mloginregtemp.mailingservice.service;

import jakarta.mail.MessagingException;
import ng.samuel.mloginregtemp.mailingservice.dto.MailingRequestDTO;

public interface MailingService {

    void send(MailingRequestDTO dto) throws MessagingException;

    void sendSecret(MailingRequestDTO dto) throws  MessagingException;
}
