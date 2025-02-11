package ng.samuel.mloginregtemp.authenticationservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserRequestDTO {
    @NotBlank(message = "field 'firstname' is mandatory : it can not be blank")
    private String fullName;

    @NotBlank(message = "field 'email' is mandatory : it can not be blank")
    @Email(message = "field 'email' must be well formated as 'example@mail.com'")
    private String email;

    @NotBlank(message = "field 'password' is mandatory : it can not be blank")
    private String password;

}
