package br.com.compassuol.sp.challenge.msorder.service;

import br.com.compassuol.sp.challenge.msorder.entities.DeliveryAddress;
import br.com.compassuol.sp.challenge.msorder.entities.Order;
import br.com.compassuol.sp.challenge.msorder.entities.enums.Status;
import br.com.compassuol.sp.challenge.msorder.exceptions.ResourceNotFoundException;
import br.com.compassuol.sp.challenge.msorder.feignclient.AddressFeign;
import br.com.compassuol.sp.challenge.msorder.feignclient.ProductFeign;
import br.com.compassuol.sp.challenge.msorder.payload.*;
import br.com.compassuol.sp.challenge.msorder.repositories.OrderRepository;
import br.com.compassuol.sp.challenge.msorder.services.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private ProductFeign productFeign;

    @Mock
    private AddressFeign addressFeign;


    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllOrders() {
        int pageNumber = 0;
        int pageSize = 10;
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(1L, 101L, List.of(201L, 202L), new DeliveryAddress(), Status.PENDING));
        orders.add(new Order(2L, 102L, List.of(203L, 204L), new DeliveryAddress(), Status.SHIPPED));
        Page<Order> orderPage = new PageImpl<>(orders);

        when(orderRepository.findAll(any(Pageable.class))).thenReturn(orderPage);

        OrderResponse orderResponse = orderService.getAllOrders(pageNumber, pageSize);

        assertEquals(2, orderResponse.getContent().size());
        assertEquals(0, orderResponse.getPageNumber());
        assertEquals(2, orderResponse.getPageSize());
        assertEquals(2, orderResponse.getTotalElements());
        assertEquals(1, orderResponse.getTotalPages());
    }

    @Test
    void testGetById() {
        // Given
        Long orderId = 1L;
        Order order = new Order(orderId, 101L, List.of(201L, 202L), new DeliveryAddress(), Status.PENDING);
        ProductDto productDto1 = new ProductDto(201L, "Product1", "Description1", BigDecimal.valueOf(10.0));
        ProductDto productDto2 = new ProductDto(202L, "Product2", "Description2", BigDecimal.valueOf(20.0));

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(productFeign.findAllProducts(List.of(201L, 202L))).thenReturn(List.of(productDto1, productDto2));

        // When
        OrderDetailsProduct orderDetailsProduct = orderService.getById(orderId);

        // Then
        assertEquals(orderId, orderDetailsProduct.getId());
        assertEquals(101L, orderDetailsProduct.getUserId());
        assertEquals(List.of(productDto1, productDto2), orderDetailsProduct.getProducts());
        assertNotNull(orderDetailsProduct.getDeliveryAddress());
        assertEquals(Status.PENDING, orderDetailsProduct.getStatus());
    }

    @Test
    public void testCreateOrder_ValidOrder() throws InterruptedException {// Given
        AddressResponse addressResponse = new AddressResponse();
        addressResponse.setLocalidade("City");
        addressResponse.setUf("State");

        when(addressFeign.searchAddress(anyString())).thenReturn(addressResponse);

        when(productFeign.findAllProducts(anyList())).thenReturn(Collections.emptyList());

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setId(1L);
        orderRequest.setUserId(123L);
        orderRequest.setProducts(Collections.singletonList(101L));
        orderRequest.setDeliveryAddress(new AddressRequest("12345678", "Street1", "Complement1", "District1"));

        assertThrows(ResourceNotFoundException.class, () -> orderService.createOrder(orderRequest));
    }

    @Test
    public void testCreateOrder_InvalidOrder() {
        AddressResponse addressResponse = new AddressResponse();
        addressResponse.setLocalidade("City");
        addressResponse.setUf("State");

        when(addressFeign.searchAddress(anyString())).thenReturn(addressResponse);

        orderService.setIsValid(false);

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setId(1L);
        orderRequest.setUserId(123L);
        orderRequest.setProducts(Collections.singletonList(101L));

        assertThrows(ResourceNotFoundException.class, () -> orderService.createOrder(orderRequest));
    }

    @Test
    public void testUpdateStatus_ValidOrder() {
        Long orderId = 1L;
        OrderStatus orderStatus = new OrderStatus(Status.SHIPPED);

        Order existingOrder = new Order();
        existingOrder.setId(orderId);
        existingOrder.setStatus(Status.PENDING);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(existingOrder));

        Order updatedOrder = new Order();
        updatedOrder.setId(orderId);
        updatedOrder.setStatus(Status.SHIPPED);
        when(orderRepository.save(any(Order.class))).thenReturn(updatedOrder);

        OrderDto orderDto = orderService.updateStatus(orderId, orderStatus);

        assertNotNull(orderDto);
        assertEquals(orderId, orderDto.getId());
        assertEquals(Status.SHIPPED, orderDto.getStatus());
    }

    @Test
    public void testUpdateStatus_OrderNotFound() {
        Long orderId = 1L;
        OrderStatus orderStatus = new OrderStatus(Status.SHIPPED);

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.updateStatus(orderId, orderStatus));
    }
}






