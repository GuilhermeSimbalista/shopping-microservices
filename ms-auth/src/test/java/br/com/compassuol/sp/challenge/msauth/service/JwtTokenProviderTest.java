package br.com.compassuol.sp.challenge.msauth.service;

import br.com.compassuol.sp.challenge.msauth.exceptions.AuthAPIException;
import br.com.compassuol.sp.challenge.msauth.security.JwtTokenProvider;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.TestPropertySource;

import java.security.Key;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@TestPropertySource(properties = {
        "jwt.secret=mysecretkey",
        "jwt.expiration=3600000" // 1 hour
})
public class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private Authentication authentication;

    @BeforeEach
    public void setup() {
        jwtTokenProvider = new JwtTokenProvider();
    }

    @Test
    public void testValidateMalformedToken() {
        // Given
        String malformedToken = "malformed_token";

        // When and Then
        assertThrows(AuthAPIException.class, () -> jwtTokenProvider.validateToken(malformedToken));
    }

    private String createToken(String username) {
        Key key = Keys.hmacShaKeyFor("mysecretkey".getBytes());

        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + 3600000); // 1 hour

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expirationDate)
                .signWith(key)
                .compact();
    }

    private String createExpiredToken(String username) {
        Key key = Keys.hmacShaKeyFor("mysecretkey".getBytes());

        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() - 3600000); // 1 hour ago

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expirationDate)
                .signWith(key)
                .compact();
    }

}
