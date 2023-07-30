package br.com.compassuol.sp.challenge.msuser.services;

import br.com.compassuol.sp.challenge.msuser.entities.Role;
import br.com.compassuol.sp.challenge.msuser.entities.User;
import br.com.compassuol.sp.challenge.msuser.exceptions.ResourceNotFoundException;
import br.com.compassuol.sp.challenge.msuser.exceptions.UniqueException;
import br.com.compassuol.sp.challenge.msuser.payload.UserDto;
import br.com.compassuol.sp.challenge.msuser.repositories.UserRepository;
import jakarta.transaction.RollbackException;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public UserDto findByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User", "Username", username));
        return mapToDTO(user);
    }

    public UserDto createUser(@Valid UserDto userDto) {
        try {
            User user = new User();
            user.setName(userDto.getName());
            user.setUsername(userDto.getUsername());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setRole(Role.ROLE_USER.toString());

            userRepository.save(user);

            return mapToDTO(user);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueException("There is already a user", userDto.getUsername());
        }
    }

    private UserDto mapToDTO(User user) {
        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setRole(user.getRole());

        return userDto;
    }
}
