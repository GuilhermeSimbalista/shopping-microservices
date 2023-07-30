package br.com.compassuol.sp.challenge.msauth.controller;

import br.com.compassuol.sp.challenge.msauth.exceptions.ResourceNotFoundException;
import br.com.compassuol.sp.challenge.msauth.payload.JwtAuthResponse;
import br.com.compassuol.sp.challenge.msauth.payload.LoginDto;
import br.com.compassuol.sp.challenge.msauth.services.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthControllerTest {


    private AuthController authController;
    private AuthService authService;

    @BeforeEach
    public void setup() {
        authService = mock(AuthService.class);
        authController = new AuthController(authService);
    }

    @Test
    public void testLogin_Success() {
        LoginDto loginDto = new LoginDto("username", "password");
        String token = "sampleToken";
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        when(authService.login(loginDto)).thenReturn(token);
        ResponseEntity<JwtAuthResponse> responseEntity = authController.login(loginDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(token, responseEntity.getBody().getAccessToken());
    }

}

