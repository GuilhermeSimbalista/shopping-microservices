package br.com.compassuol.sp.challenge.msauth.controller;

import br.com.compassuol.sp.challenge.msauth.entities.User;
import br.com.compassuol.sp.challenge.msauth.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    private UserService userService;
    private UserController userController;

    @BeforeEach
    public void setup() {
        userService = mock(UserService.class);
        userController = new UserController(userService);
    }

    @Test
    public void testGetByUsername_Success() {
        String username = "sampleUsername";
        User sampleUser = new User();
        sampleUser.setUsername(username);
        when(userService.getByUsername(username)).thenReturn(sampleUser);

        ResponseEntity<User> responseEntity = userController.getByUsername(username);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(sampleUser, responseEntity.getBody());
        verify(userService, times(1)).getByUsername(username);
    }

}

