package ng.samuel.mloginregtemp.authenticationservice.repository;

import ng.samuel.mloginregtemp.authenticationservice.entity.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    Optional<ConfirmationToken> findByToken(String token);
}
