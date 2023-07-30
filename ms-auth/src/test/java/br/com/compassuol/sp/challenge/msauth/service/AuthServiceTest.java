package br.com.compassuol.sp.challenge.msauth.service;

import br.com.compassuol.sp.challenge.msauth.exceptions.AuthAPIException;
import br.com.compassuol.sp.challenge.msauth.feignClients.FeignConfig;
import br.com.compassuol.sp.challenge.msauth.payload.LoginDto;
import br.com.compassuol.sp.challenge.msauth.security.JwtTokenProvider;
import br.com.compassuol.sp.challenge.msauth.services.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private FeignConfig feignConfig;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLogin_SuccessfulAuthentication() {
        LoginDto loginDto = new LoginDto("testUser", "testPassword");
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        String expectedToken = "generated-token";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtTokenProvider.generateToken(any(Authentication.class))).thenReturn(expectedToken);

        String token = authService.login(loginDto);

        assertEquals(expectedToken, token);
    }

    @Test
    public void testLogin_FailedAuthentication() {
        LoginDto loginDto = new LoginDto("testUser", "wrongPassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new AuthAPIException(HttpStatus.UNAUTHORIZED, "Authentication failed"));

        assertThrows(AuthAPIException.class, () -> authService.login(loginDto));
    }
}


