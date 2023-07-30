package br.com.compassuol.sp.challenge.msorder.payload;

import br.com.compassuol.sp.challenge.msorder.entities.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatus {

    private Status status;
}
