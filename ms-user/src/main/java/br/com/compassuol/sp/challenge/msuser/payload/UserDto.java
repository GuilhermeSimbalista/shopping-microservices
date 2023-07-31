package br.com.compassuol.sp.challenge.msuser.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "UserDto Model Information")
public class UserDto {

    private Long id;

    @Schema(description = "Name")
    @NotBlank(message = "name is mandatory")
    private String name;

    @Schema(description = "Username")
    @NotBlank(message = "Username is mandatory")
    private String username;

    @Schema(description = "Password")
    @NotBlank(message = "password is mandatory")
    private String password;

    @Schema(description = "User Role")
    private String role;
}
