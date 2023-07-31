package br.com.compassuol.sp.challenge.msorder.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "ProductDTO Model Information")
public class ProductDto {

    @Schema(description = "Product Id")
    private Long id;

    @Schema(description = "Product Name")
    private String name;

    @Schema(description = "Product Description")
    private String description;

    @Schema(description = "Product Price")
    private BigDecimal price;
}
