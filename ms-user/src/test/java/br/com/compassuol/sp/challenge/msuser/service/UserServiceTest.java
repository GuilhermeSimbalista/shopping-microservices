package br.com.compassuol.sp.challenge.msuser.service;

import br.com.compassuol.sp.challenge.msuser.entities.User;
import br.com.compassuol.sp.challenge.msuser.exceptions.ResourceNotFoundException;
import br.com.compassuol.sp.challenge.msuser.payload.UserDto;
import br.com.compassuol.sp.challenge.msuser.repositories.UserRepository;
import br.com.compassuol.sp.challenge.msuser.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = Arrays.asList(
                new User(1L, "John Doe", "john", "encodedPassword", "ROLE_USER"),
                new User(2L, "Jane Smith", "jane", "encodedPassword", "ROLE_USER")
        );

        when(userRepository.findAll()).thenReturn(users);

        List<UserDto> userDtos = userService.getAllUsers();

        assertEquals(2, userDtos.size());
        assertEquals("John Doe", userDtos.get(0).getName());
        assertEquals("john", userDtos.get(0).getUsername());
    }

    @Test
    public void testFindByUsername_ExistingUser() {
        String username = "john";
        User user = new User(1L, "John Doe", username, "encodedPassword", "ROLE_USER");

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        UserDto userDto = userService.findByUsername(username);

        assertEquals("John Doe", userDto.getName());
        assertEquals("john", userDto.getUsername());
    }

    @Test
    public void testFindByUsername_NonExistingUser() {
        String username = "nonExistingUser";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = org.junit.jupiter.api.Assertions.assertThrows(
                ResourceNotFoundException.class,
                () -> userService.findByUsername(username)
        );
        assertEquals("User Not Found with Username : 'nonExistingUser'", exception.getMessage());
    }

    @Test
    public void testCreateUser() {
        UserDto userDto = new UserDto();
        userDto.setName("John Doe");
        userDto.setUsername("john");
        userDto.setPassword("password");

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        UserDto createdUserDto = userService.createUser(userDto);

        assertEquals("John Doe", createdUserDto.getName());
        assertEquals("john", createdUserDto.getUsername());

        verify(userRepository, times(1)).save(any(User.class));
    }
}

