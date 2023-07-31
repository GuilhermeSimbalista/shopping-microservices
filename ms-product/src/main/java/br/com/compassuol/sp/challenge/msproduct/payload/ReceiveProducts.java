package br.com.compassuol.sp.challenge.msproduct.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Receives the products ids for validation")
public class ReceiveProducts {

    @Schema(description = "Product Id List")
    private List<Long> products;
}
