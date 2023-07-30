package br.com.compassuol.sp.challenge.msauth.controller;

import br.com.compassuol.sp.challenge.msauth.payload.JwtAuthResponse;
import br.com.compassuol.sp.challenge.msauth.payload.LoginDto;
import br.com.compassuol.sp.challenge.msauth.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }

    @GetMapping
    public String get() {
        return "Teste";
    }
}
