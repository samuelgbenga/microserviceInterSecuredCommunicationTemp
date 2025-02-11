package ng.samuel.mloginregtemp.mailingservice.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ng.samuel.mloginregtemp.mailingservice.dto.MailingRequestDTO;
import ng.samuel.mloginregtemp.mailingservice.service.MailingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/mailing")
@RestController
@RequiredArgsConstructor
public class MailingController {

    private final MailingService mailingService;

    @PostMapping("/send")
    public void send(@RequestBody @Valid MailingRequestDTO dto){
        try{
            mailingService.send(dto);
        }catch (Exception e){
         throw new RuntimeException("Mailing Error");
        }

    }
}
