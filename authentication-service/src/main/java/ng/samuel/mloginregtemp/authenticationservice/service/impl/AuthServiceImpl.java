package ng.samuel.mloginregtemp.authenticationservice.service.impl;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import ng.samuel.mloginregtemp.authenticationservice.config.JwtService;
import ng.samuel.mloginregtemp.authenticationservice.dto.LoginRequestDTO;
import ng.samuel.mloginregtemp.authenticationservice.dto.LoginResponseDto;
import ng.samuel.mloginregtemp.authenticationservice.dto.RegistrationResponseDto;
import ng.samuel.mloginregtemp.authenticationservice.dto.UserRequestDTO;
import ng.samuel.mloginregtemp.authenticationservice.entity.ConfirmationToken;
import ng.samuel.mloginregtemp.authenticationservice.entity.User;
import ng.samuel.mloginregtemp.authenticationservice.enums.Role;
import ng.samuel.mloginregtemp.authenticationservice.exception.AlreadyExistException;
import ng.samuel.mloginregtemp.authenticationservice.exception.NotAuthenticatedException;
import ng.samuel.mloginregtemp.authenticationservice.exception.NotEnabledException;
import ng.samuel.mloginregtemp.authenticationservice.exception.NotFoundException;
import ng.samuel.mloginregtemp.authenticationservice.repository.ConfirmationTokenRepository;
import ng.samuel.mloginregtemp.authenticationservice.repository.UserRepository;
import ng.samuel.mloginregtemp.authenticationservice.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service
@RequiredArgsConstructor
@Builder
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final ConfirmationTokenRepository tokenRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final String LINK = "http://localhost:8888/auth/confirm?token=";


    @Override
    public RegistrationResponseDto register(UserRequestDTO requestDTO) {
        Optional<User> existingUser = userRepository.findByEmail(requestDTO.getEmail());
        if (existingUser.isPresent()) {
            throw new AlreadyExistException("User already exists, please Login");
        }

        // save user to the db
        User savedUser = userRepository.save(initialMapFrom(requestDTO));

        // get string confirmation url
        String confirmationUrl = LINK + getConfirmationToken(savedUser);

        // Todo: to include async mailing service method

        return new RegistrationResponseDto("001", "Registration successful check check you email to confirm " + confirmationUrl);

    }

    @Override
    public LoginResponseDto login(LoginRequestDTO dto) {

        Authentication authenticationRequest =  new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        Authentication authenticationResponse = authenticationManager.authenticate(authenticationRequest);
        if (authenticationResponse.isAuthenticated()) {
            User user = getUserByEmail(dto.email());
            if(!user.isEnabled()){
                throw new NotEnabledException(String.format("User %s is not enabled", dto.email()));
            }

            final String jwt = jwtService.generateToken(user);

            return new LoginResponseDto("002" ,"Login successful check your email",jwt);
        }else{
            throw new NotAuthenticatedException("User not authenticated");
        }
    }

    @Override
    public boolean enableUser(String token) {
        User user = getUserByToken(token);
        if(user.isEnabled()){
            user.setEnabled(Boolean.FALSE);

        }else{
            user.setEnabled(Boolean.TRUE);

        }
        userRepository.save(user);
        return user.isEnabled();
    }




    private User getUserByEmail(String email){
        User user = userRepository.findByEmail(email).orElseThrow(()->
                new NotFoundException("No user with email :" + email));
        return user;

    }


    // initial mapping from dot
    private User initialMapFrom (UserRequestDTO dto){
        Set<Role> roles = new HashSet<>();

        roles.add(Role.USER);

        return User.builder()
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .enabled(false)
                .password(passwordEncoder.encode(dto.getPassword()))
                .roles(roles)
                .build();
    }

    private String getConfirmationToken(User user){
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        // save this new confirmation token
        tokenRepository.save(confirmationToken);
        // return the confirmation token
        return confirmationToken.getToken();
    }

    // find user by confirmation token
    private User getUserByToken(String token){
       ConfirmationToken confirmationToken = tokenRepository.findByToken(token).
               orElseThrow(()-> new NotFoundException(String.format("user with token: %s not found", token)));
       return confirmationToken.getUsers();
    }



    // Todo: Email settings later on
}
