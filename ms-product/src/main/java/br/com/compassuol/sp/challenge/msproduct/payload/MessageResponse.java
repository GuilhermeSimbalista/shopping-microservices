package br.com.compassuol.sp.challenge.msproduct.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Schema(description = "MessageResponse Model Information")
public class MessageResponse {

    @Schema(description = "Receive a List of Products")
    private List<ProductDto> products;

    @Schema(description = "Check if You own the Products by id")
    private boolean productsAvailable;
}
