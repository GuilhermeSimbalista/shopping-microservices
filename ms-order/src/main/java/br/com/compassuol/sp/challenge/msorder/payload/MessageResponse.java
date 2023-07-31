package br.com.compassuol.sp.challenge.msorder.payload;

import feign.Feign;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Message Response Feign Client Model Information")
public class MessageResponse {


    @Schema(description = "Product List")
    private List<ProductDto> products;

    @Schema(description = "Product is valid")
    private boolean productsAvailable;
}
