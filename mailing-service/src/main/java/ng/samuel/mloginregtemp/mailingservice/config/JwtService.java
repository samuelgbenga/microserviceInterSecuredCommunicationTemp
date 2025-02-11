package ng.samuel.mloginregtemp.mailingservice.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class JwtService {

    private final ApplicationProperties  properties;

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(properties.getJwtSecret());

        return Keys.hmacShaKeyFor(keyBytes);
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {

        final Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }


//    public Boolean isTokenValid(String token, UserDetails userDetails){
//        final String userName = extractUsername(token);
//
//        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
//    }

    public String extractUsername(String token){
        return getClaim(token, Claims::getSubject);
    }

    public List<String> extractRoles(String token) {
        return getClaim(token, claims -> {
            List<Map<String, String>> roles = claims.get("roles", List.class);
            return roles.stream()
                    .map(roleMap -> roleMap.get("authority")) // Extract authority value
                    .collect(Collectors.toList());
        });
    }

    public boolean isTokenExpired(String token) {
        return  extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token){
        return getClaim(token, Claims::getExpiration);
    }
}
