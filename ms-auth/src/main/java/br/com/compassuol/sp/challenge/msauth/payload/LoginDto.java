package br.com.compassuol.sp.challenge.msauth.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "LoginDTO Model Information")
public class LoginDto {

    @Schema(description = "Username For Login")
    private String username;

    @Schema(description = "Password For Login")
    private String password;
}
