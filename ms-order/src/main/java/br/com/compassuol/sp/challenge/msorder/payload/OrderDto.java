package br.com.compassuol.sp.challenge.msorder.payload;

import br.com.compassuol.sp.challenge.msorder.entities.DeliveryAddress;
import br.com.compassuol.sp.challenge.msorder.entities.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "OrderDto Model Information")
public class OrderDto {

    @Schema(description = "Order Id")
    private Long id;

    @Schema(description = "User Id")
    private Long userId;

    @Schema(description = "Product Id List")
    private List<Long> products;

    @Schema(description = "Delivery Address")
    private DeliveryAddress deliveryAddress;

    @Schema(description = "Order Status")
    @Enumerated(EnumType.STRING)
    private Status status;
}
