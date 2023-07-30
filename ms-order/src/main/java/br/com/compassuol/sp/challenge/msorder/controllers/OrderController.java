package br.com.compassuol.sp.challenge.msorder.controllers;

import br.com.compassuol.sp.challenge.msorder.payload.*;
import br.com.compassuol.sp.challenge.msorder.services.OrderService;
import br.com.compassuol.sp.challenge.msorder.utils.OrderConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<OrderResponse> getAllOrders(
            @RequestParam(value = "pageNumber", defaultValue = OrderConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = OrderConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize ) {

        OrderResponse orderResponse = orderService.getAllOrders(pageNumber, pageSize);
        return ResponseEntity.ok(orderResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailsProduct> getOrderById(@PathVariable Long id) {
        OrderDetailsProduct order = orderService.getById(id);
        return ResponseEntity.ok(order);
    }

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderRequest orderRequest) {
        OrderDto orderDto = orderService.createOrder(orderRequest);

        URI uri = URI.create("/orders/" + orderDto.getId());

        return ResponseEntity.created(uri).body(orderDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long id, @RequestBody OrderStatus orderStatus) {
        OrderDto orderDto = orderService.updateStatus(id, orderStatus);

        return ResponseEntity.ok(orderDto);
    }
}
