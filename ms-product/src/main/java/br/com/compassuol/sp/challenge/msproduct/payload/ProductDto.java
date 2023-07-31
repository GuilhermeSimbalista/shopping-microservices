package br.com.compassuol.sp.challenge.msproduct.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "ProductDTO Model Information")
public class ProductDto {

    private Long id;

    @Schema(description = "Product Name")
    @NotBlank(message = "name is mandatory")
    private String name;

    @Schema(description = "Product Description")
    @NotBlank(message = "description is mandatory")
    private String description;

    @Schema(description = "Product Price")
    @NotNull(message = "price is not null")
    private BigDecimal price;
}
