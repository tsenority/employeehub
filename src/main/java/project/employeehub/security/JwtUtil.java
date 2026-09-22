package project.employeehub.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component //Creates the bean for dependency just like @Service
public class JwtUtil {

    private final String secretKey =  "my-super-secret-key-for-employeehub-jwt-authentication-2026";
    // creating secret key, The key needs to be sufficiently long for the cryptographic algorithm.

    //Create the method that generates the JWT
    public String generateToken(String username) {

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date()) //current time, rite now.
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))//"This JWT expires 1 hour from now."
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }
    //  extract the username from the JWT
    public String extractUsername(String token) {

        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
