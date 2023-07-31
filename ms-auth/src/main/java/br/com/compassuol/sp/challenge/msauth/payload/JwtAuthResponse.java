package br.com.compassuol.sp.challenge.msauth.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Jwt Auth Response Model Information")
public class JwtAuthResponse {

    @Schema(description = "Access Token")
    private String accessToken;

    @Schema(description = "Token Type")
    private String tokenType = "Bearer";
}
