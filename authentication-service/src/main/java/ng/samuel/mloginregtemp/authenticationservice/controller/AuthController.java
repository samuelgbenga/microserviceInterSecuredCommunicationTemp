package ng.samuel.mloginregtemp.authenticationservice.controller;


import lombok.RequiredArgsConstructor;
import ng.samuel.mloginregtemp.authenticationservice.dto.LoginRequestDTO;
import ng.samuel.mloginregtemp.authenticationservice.dto.LoginResponseDto;
import ng.samuel.mloginregtemp.authenticationservice.dto.RegistrationResponseDto;
import ng.samuel.mloginregtemp.authenticationservice.dto.UserRequestDTO;
import ng.samuel.mloginregtemp.authenticationservice.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponseDto> register(@RequestBody UserRequestDTO dto){
        RegistrationResponseDto response = authService.register(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO dto){
        try {
            LoginResponseDto response = authService.login(dto);
            return ResponseEntity.ok(response);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(String.format("Invalid credentials : %s", e.getMessage()));
        }


    }

    // Enable with the confirmation token
    @GetMapping("/confirm")
    public ResponseEntity<String> enable(@RequestParam(name = "token") String token){
        boolean isEnabled = authService.enableUser(token);
        return isEnabled ? ResponseEntity.ok("User Enabled") : ResponseEntity.ok("User Revoked");
    }

    @GetMapping("/testing")
    public ResponseEntity<String> testing(){
        return ResponseEntity.ok("it is working");
    }

}
