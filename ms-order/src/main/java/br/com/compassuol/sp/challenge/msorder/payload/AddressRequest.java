package br.com.compassuol.sp.challenge.msorder.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Address Request Model Information")
public class AddressRequest {

    @Schema(description = "Zip Code")
    private String zipCode;

    @Schema(description = "Street")
    private String street;

    @Schema(description = "Complement")
    private String complement;

    @Schema(description = "District")
    private String district;

}
