package br.com.compassuol.sp.challenge.msproduct.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Product Response With Pagination")
public class ProductResponse {

    @Schema(description = "Product List")
    private List<ProductDto> content;

    @Schema(description = "Page Number")
    private int pageNumber;

    @Schema(description = "Page Size")
    private int pageSize;

    @Schema(description = "Total Elements")
    private Long totalElements;

    @Schema(description = "Total Pages")
    private int totalPages;

    @Schema(description = "Last Page")
    private boolean last;
}
