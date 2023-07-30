package br.com.compassuol.sp.challenge.msproduct.payload;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class MessageResponse {

    private List<ProductDto> products;
    private boolean productsAvailable;
}
