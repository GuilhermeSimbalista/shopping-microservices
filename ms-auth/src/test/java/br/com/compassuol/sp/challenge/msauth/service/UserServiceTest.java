package br.com.compassuol.sp.challenge.msauth.service;

import br.com.compassuol.sp.challenge.msauth.entities.User;
import br.com.compassuol.sp.challenge.msauth.exceptions.ResourceNotFoundException;
import br.com.compassuol.sp.challenge.msauth.feignClients.FeignConfig;
import br.com.compassuol.sp.challenge.msauth.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private FeignConfig feignConfig;
    private UserService userService;

    @BeforeEach
    public void setup() {
        feignConfig = mock(FeignConfig.class);
        userService = new UserService(feignConfig);
    }

    @Test
    public void testGetByUsername_Success() {
        String username = "sampleUsername";
        User sampleUser = new User();
        sampleUser.setUsername(username);
        when(feignConfig.getByUsername(username)).thenReturn(Optional.of(sampleUser));

        User resultUser = userService.getByUsername(username);

        assertEquals(sampleUser, resultUser);
        verify(feignConfig, times(1)).getByUsername(username);
    }

    @Test
    public void testGetByUsername_UserNotFound() {
        String username = "nonExistingUser";
        when(feignConfig.getByUsername(username)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getByUsername(username));
        verify(feignConfig, times(1)).getByUsername(username);
    }
}
