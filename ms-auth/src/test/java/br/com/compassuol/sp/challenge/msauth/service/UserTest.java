package br.com.compassuol.sp.challenge.msauth.service;

import br.com.compassuol.sp.challenge.msauth.entities.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {


    @Test
    public void testUserGettersAndSetters() {
        // Given
        Long id = 1L;
        String name = "John Doe";
        String username = "john.doe";
        String password = "password123";
        String role = "ROLE_USER";

        // When
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);

        // Then
        assertEquals(id, user.getId());
        assertEquals(name, user.getName());
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(role, user.getRole());
    }

    @Test
    public void testUserConstructor() {
        // Given
        Long id = 1L;
        String name = "John Doe";
        String username = "john.doe";
        String password = "password123";
        String role = "ROLE_USER";

        // When
        User user = new User(id, name, username, password, role);

        // Then
        assertEquals(id, user.getId());
        assertEquals(name, user.getName());
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(role, user.getRole());
    }
}

