package br.com.compassuol.sp.challenge.msuser.controllers;

import br.com.compassuol.sp.challenge.msuser.payload.UserDto;
import br.com.compassuol.sp.challenge.msuser.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<UserDto> getByUsername(@RequestParam String username) {
        return ResponseEntity.ok(userService.findByUsername(username));
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto newUser = userService.createUser(userDto);
        URI uri = URI.create("/users/" + newUser.getId());

        return ResponseEntity.created(uri).body(newUser);
    }




}
