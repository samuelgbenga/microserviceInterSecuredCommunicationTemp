package ng.samuel.mloginregtemp.authenticationservice.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table
public class ConfirmationToken extends BaseClass{

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime expiryDate;


    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User users;

    public ConfirmationToken(User user){
        this.token = UUID.randomUUID().toString();
        this.expiryDate = LocalDateTime.now().plusDays(1);
        this.users = user;
    }
}
