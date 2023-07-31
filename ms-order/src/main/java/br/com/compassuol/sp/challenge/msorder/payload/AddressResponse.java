package br.com.compassuol.sp.challenge.msorder.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Address Response Feign Client Model Information")
public class AddressResponse {

    @Schema(description = "City")
    private String localidade;

    @Schema(description = "State")
    private String uf;

}
