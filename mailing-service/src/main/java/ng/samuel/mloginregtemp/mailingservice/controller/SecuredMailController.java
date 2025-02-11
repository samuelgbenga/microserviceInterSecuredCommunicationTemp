package ng.samuel.mloginregtemp.mailingservice.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ng.samuel.mloginregtemp.mailingservice.dto.MailingRequestDTO;
import ng.samuel.mloginregtemp.mailingservice.service.MailingService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/securedMailing")
@RestController
@RequiredArgsConstructor
public class SecuredMailController {

    private final MailingService mailingService;

    @PostMapping
    public void send(@RequestBody @Valid MailingRequestDTO dto){
        try{
            mailingService.sendSecret(dto);
        }catch (Exception e){
            throw new RuntimeException("Mailing Error");
        }

    }
}
