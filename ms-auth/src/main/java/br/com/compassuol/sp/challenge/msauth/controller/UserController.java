package br.com.compassuol.sp.challenge.msauth.controller;

import br.com.compassuol.sp.challenge.msauth.entities.User;
import br.com.compassuol.sp.challenge.msauth.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService authService;


    public UserController(UserService authService) {
        this.authService = authService;
    }

    @GetMapping(value = "/search")
    public ResponseEntity<User> getByUsername(@RequestParam String username) {
        User user = authService.getByUsername(username);
        return ResponseEntity.ok(user);
    }
}
