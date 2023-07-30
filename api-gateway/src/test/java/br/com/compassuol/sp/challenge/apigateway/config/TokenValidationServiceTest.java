package br.com.compassuol.sp.challenge.apigateway.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.security.Key;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
        "jwt.secret=d1068beeb96a6a79937661d5cc9f290dddaa5730e64b7ab2b238078a1194c614"
})
public class TokenValidationServiceTest {

    private TokenValidationService tokenValidationService;

    @BeforeEach
    public void setup() {
        tokenValidationService = new TokenValidationService();
    }

    @Test
    public void testValidateToken_ExpiredToken() {
        // Given
        String expiredToken = createExpiredToken("john_doe");

        // When
        boolean isValidToken = tokenValidationService.validateToken(expiredToken);

        // Then
        assertFalse(isValidToken);
    }

    @Test
    public void testValidateToken_MalformedToken() {
        // Given
        String malformedToken = "malformed_token";

        // When
        boolean isValidToken = tokenValidationService.validateToken(malformedToken);

        // Then
        assertFalse(isValidToken);
    }

    private String createToken(String username) {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .signWith(key)
                .compact();
    }

    private String createExpiredToken(String username) {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() - 3600000)) // 1 hour ago
                .signWith(key)
                .compact();
    }
}
