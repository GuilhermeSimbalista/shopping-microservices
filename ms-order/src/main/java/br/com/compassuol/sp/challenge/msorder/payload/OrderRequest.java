package br.com.compassuol.sp.challenge.msorder.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    private Long id;

    @NotBlank(message = "user Id is mandatory")
    private Long userId;

    @NotBlank(message = "Products Ids is mandatory")
    private List<Long> products;

    @NotBlank(message = "Delivery Address is mandatory")
    private AddressRequest deliveryAddress;

}
