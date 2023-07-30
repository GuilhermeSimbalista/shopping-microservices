package br.com.compassuol.sp.challenge.msorder.payload;

import lombok.Data;

import java.util.List;

@Data
public class MessageResponse {

    private List<ProductDto> products;
    private boolean productsAvailable;
}
