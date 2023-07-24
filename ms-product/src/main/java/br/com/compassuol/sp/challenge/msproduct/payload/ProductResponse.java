package br.com.compassuol.sp.challenge.msproduct.payload;

import lombok.Data;

import java.util.List;

@Data
public class ProductResponse {

    private List<ProductDto> content;
    private int pageNumber;
    private int pageSize;
    private Long totalElements;
    private int totalPages;
    private boolean last;
}
