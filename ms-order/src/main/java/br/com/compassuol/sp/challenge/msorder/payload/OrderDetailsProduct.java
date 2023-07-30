package br.com.compassuol.sp.challenge.msorder.payload;

import br.com.compassuol.sp.challenge.msorder.entities.DeliveryAddress;
import br.com.compassuol.sp.challenge.msorder.entities.enums.Status;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsProduct {

    private Long id;
    private Long userId;
    private List<ProductDto> products;
    private DeliveryAddress deliveryAddress;
    @Enumerated(EnumType.STRING)
    private Status status;
}
