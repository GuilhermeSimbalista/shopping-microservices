package br.com.compassuol.sp.challenge.msorder.controllers;

import br.com.compassuol.sp.challenge.msorder.payload.*;
import br.com.compassuol.sp.challenge.msorder.services.OrderService;
import br.com.compassuol.sp.challenge.msorder.utils.OrderConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/orders")
@Tag(name = "Order Controller")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(
            summary = "Get All Orders REST API",
            description = "Get All Orders REST API is used to get all orders into database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @GetMapping
    public ResponseEntity<OrderResponse> getAllOrders(
            @RequestParam(value = "pageNumber", defaultValue = OrderConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = OrderConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize ) {

        OrderResponse orderResponse = orderService.getAllOrders(pageNumber, pageSize);
        return ResponseEntity.ok(orderResponse);
    }

    @Operation(
            summary = "Get Order By Id REST API",
            description = "Get Order By Id REST API is used to get single order and details the products into database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailsProduct> getOrderById(@PathVariable Long id) {
        OrderDetailsProduct order = orderService.getById(id);
        return ResponseEntity.ok(order);
    }

    @Operation(
            summary = "Create Order REST API",
            description = "Create Order REST API is used to save order into database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderRequest orderRequest) {
        OrderDto orderDto = orderService.createOrder(orderRequest);

        URI uri = URI.create("/orders/" + orderDto.getId());

        return ResponseEntity.created(uri).body(orderDto);
    }

    @Operation(
            summary = "Update Order REST API",
            description = "Update Order REST API is used to update order into database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long id, @RequestBody OrderStatus orderStatus) {
        OrderDto orderDto = orderService.updateStatus(id, orderStatus);

        return ResponseEntity.ok(orderDto);
    }
}
