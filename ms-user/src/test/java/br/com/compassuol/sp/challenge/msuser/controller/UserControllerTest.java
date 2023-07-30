package br.com.compassuol.sp.challenge.msuser.controller;

import br.com.compassuol.sp.challenge.msuser.controllers.UserController;
import br.com.compassuol.sp.challenge.msuser.exceptions.ResourceNotFoundException;
import br.com.compassuol.sp.challenge.msuser.payload.UserDto;
import br.com.compassuol.sp.challenge.msuser.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllUsers() {
        // Given
        List<UserDto> users = Arrays.asList(
                new UserDto(1L, "John Doe", "john", "encodedPassword", "ROLE_USER"),
                new UserDto(2L, "Jane Smith", "jane", "encodedPassword", "ROLE_USER")
        );

        when(userService.getAllUsers()).thenReturn(users);

        // When
        ResponseEntity<List<UserDto>> responseEntity = userController.getAllUsers();

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, responseEntity.getBody().size());
        // Add more assertions as needed for other properties
    }

    @Test
    public void testGetByUsername_ExistingUser() {
        // Given
        String username = "john";
        UserDto userDto = new UserDto(1L, "John Doe", username, "encodedPassword", "ROLE_USER");

        when(userService.findByUsername(username)).thenReturn(userDto);

        // When
        ResponseEntity<UserDto> responseEntity = userController.getByUsername(username);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("John Doe", responseEntity.getBody().getName());
        assertEquals("john", responseEntity.getBody().getUsername());
        // Add more assertions as needed for other properties
    }


    @Test
    public void testCreateUser() {
        // Given
        UserDto userDto = new UserDto();
        userDto.setName("John Doe");
        userDto.setUsername("john");
        userDto.setPassword("password");

        UserDto newUserDto = new UserDto(1L, "John Doe", "john", "encodedPassword", "ROLE_USER");
        when(userService.createUser(any(UserDto.class))).thenReturn(newUserDto);

        // When
        ResponseEntity<UserDto> responseEntity = userController.createUser(userDto);

        // Then
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("John Doe", responseEntity.getBody().getName());
        assertEquals("john", responseEntity.getBody().getUsername());
        // Add more assertions as needed for other properties

        URI expectedUri = URI.create("/users/1"); // Change the URI as per your implementation
        assertEquals(expectedUri, responseEntity.getHeaders().getLocation());
    }
}
