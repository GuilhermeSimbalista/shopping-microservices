package br.com.compassuol.sp.challenge.msorder.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductAvailabilityMessage {

    private List<Long> products;
}
