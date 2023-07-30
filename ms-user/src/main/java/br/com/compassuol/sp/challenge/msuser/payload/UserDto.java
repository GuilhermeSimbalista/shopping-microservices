package br.com.compassuol.sp.challenge.msuser.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    @NotBlank(message = "name is mandatory")
    private String name;

    @NotBlank(message = "Username is mandatory")
    private String username;

    @NotBlank(message = "password is mandatory")
    private String password;
    private String role;
}
