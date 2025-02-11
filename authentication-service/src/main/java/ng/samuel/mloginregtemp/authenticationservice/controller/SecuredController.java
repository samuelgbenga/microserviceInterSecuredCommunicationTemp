package ng.samuel.mloginregtemp.authenticationservice.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/secured")
@RestController
@RequiredArgsConstructor
public class SecuredController {

    @GetMapping
    public ResponseEntity<String> sendSecretMail(){
        return ResponseEntity.ok("Secrete message Sent");
    }

}
