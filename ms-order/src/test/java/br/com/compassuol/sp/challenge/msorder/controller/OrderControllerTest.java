package br.com.compassuol.sp.challenge.msorder.controller;

import br.com.compassuol.sp.challenge.msorder.controllers.OrderController;
import br.com.compassuol.sp.challenge.msorder.entities.enums.Status;
import br.com.compassuol.sp.challenge.msorder.payload.*;
import br.com.compassuol.sp.challenge.msorder.services.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.net.URI;
import java.util.Collections;

public class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testGetOrderById() {
        // Given
        Long orderId = 1L;
        OrderDetailsProduct orderDetailsProduct = new OrderDetailsProduct();

        when(orderService.getById(orderId)).thenReturn(orderDetailsProduct);

        // When
        ResponseEntity<OrderDetailsProduct> responseEntity = orderController.getOrderById(orderId);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(orderDetailsProduct, responseEntity.getBody());
    }

    @Test
    public void testCreateOrder() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setId(1L);
        orderRequest.setUserId(123L);
        orderRequest.setProducts(Collections.singletonList(1L));

        OrderDto orderDto = new OrderDto();
        orderDto.setId(1L);
        orderDto.setUserId(123L);
        orderDto.setProducts(Collections.singletonList(1L));

        when(orderService.createOrder(orderRequest)).thenReturn(orderDto);

        ResponseEntity<OrderDto> responseEntity = orderController.createOrder(orderRequest);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(orderDto, responseEntity.getBody());

        URI expectedUri = URI.create("/orders/1");
        assertEquals(expectedUri, responseEntity.getHeaders().getLocation());
    }

    @Test
    public void testUpdateOrder() {
        Long orderId = 1L;
        OrderStatus orderStatus = new OrderStatus(Status.PENDING);

        OrderDto orderDto = new OrderDto();
        orderDto.setId(orderId);

        when(orderService.updateStatus(orderId, orderStatus)).thenReturn(orderDto);

        ResponseEntity<OrderDto> responseEntity = orderController.updateOrder(orderId, orderStatus);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(orderDto, responseEntity.getBody());
    }
}

