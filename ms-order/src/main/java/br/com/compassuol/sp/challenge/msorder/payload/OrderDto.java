package br.com.compassuol.sp.challenge.msorder.payload;

import br.com.compassuol.sp.challenge.msorder.entities.DeliveryAddress;
import br.com.compassuol.sp.challenge.msorder.entities.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Long id;
    private Long userId;
    private List<Long> products;
    private DeliveryAddress deliveryAddress;
    private Status status;
}
