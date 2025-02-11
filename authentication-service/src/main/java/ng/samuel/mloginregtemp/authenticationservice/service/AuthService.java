package ng.samuel.mloginregtemp.authenticationservice.service;

import ng.samuel.mloginregtemp.authenticationservice.dto.LoginRequestDTO;
import ng.samuel.mloginregtemp.authenticationservice.dto.LoginResponseDto;
import ng.samuel.mloginregtemp.authenticationservice.dto.RegistrationResponseDto;
import ng.samuel.mloginregtemp.authenticationservice.dto.UserRequestDTO;

public interface AuthService {

    RegistrationResponseDto register(UserRequestDTO requestDTO);

    LoginResponseDto login(LoginRequestDTO requestDTO);

    boolean enableUser(String token);
}
