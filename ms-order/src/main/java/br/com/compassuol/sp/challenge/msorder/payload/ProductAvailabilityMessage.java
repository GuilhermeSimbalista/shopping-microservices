package br.com.compassuol.sp.challenge.msorder.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Product Message Response for Feign Client")
public class ProductAvailabilityMessage {

    @Schema(description = "Product Id List")
    private List<Long> products;
}
