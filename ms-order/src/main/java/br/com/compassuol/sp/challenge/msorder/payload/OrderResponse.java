package br.com.compassuol.sp.challenge.msorder.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Order Response With Pagination")
public class OrderResponse {

    @Schema(description = "Order List")
    private List<OrderDto> content;

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
