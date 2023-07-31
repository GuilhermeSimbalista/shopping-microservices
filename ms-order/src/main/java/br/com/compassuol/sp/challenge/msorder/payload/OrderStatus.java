package br.com.compassuol.sp.challenge.msorder.payload;

import br.com.compassuol.sp.challenge.msorder.entities.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Order Request Status Model Information")
public class OrderStatus {

    @Schema(description = "Order Status")
    private Status status;
}
