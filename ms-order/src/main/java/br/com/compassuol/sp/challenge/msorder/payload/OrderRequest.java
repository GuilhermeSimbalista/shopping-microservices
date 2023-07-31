package br.com.compassuol.sp.challenge.msorder.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Schema(description = "Order Request Model Information")
public class OrderRequest {

    @Schema(description = "Order Id")
    private Long id;

    @Schema(description = "User Id")
    @NotBlank(message = "user Id is mandatory")
    private Long userId;

    @Schema(description = "Product Id List")
    @NotBlank(message = "Products Ids is mandatory")
    private List<Long> products;

    @Schema(description = "Delivery Address")
    @NotBlank(message = "Delivery Address is mandatory")
    private AddressRequest deliveryAddress;

}
