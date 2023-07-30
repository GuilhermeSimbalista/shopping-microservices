package br.com.compassuol.sp.challenge.msorder.services;

import br.com.compassuol.sp.challenge.msorder.config.RabbitMQConfig;
import br.com.compassuol.sp.challenge.msorder.entities.DeliveryAddress;
import br.com.compassuol.sp.challenge.msorder.entities.Order;
import br.com.compassuol.sp.challenge.msorder.entities.enums.Status;
import br.com.compassuol.sp.challenge.msorder.exceptions.ResourceNotFoundException;
import br.com.compassuol.sp.challenge.msorder.feignclient.AddressFeign;
import br.com.compassuol.sp.challenge.msorder.feignclient.ProductFeign;
import br.com.compassuol.sp.challenge.msorder.payload.*;
import br.com.compassuol.sp.challenge.msorder.repositories.OrderRepository;
import br.com.compassuol.sp.challenge.msorder.payload.ProductAvailabilityMessage;
import jakarta.validation.Valid;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final RabbitTemplate rabbitTemplate;
    private final AddressFeign addressFeign;
    private final ProductFeign productFeign;

    public OrderService(OrderRepository orderRepository, RabbitTemplate rabbitTemplate, AddressFeign addressFeign, ProductFeign productFeign) {
        this.orderRepository = orderRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.addressFeign = addressFeign;
        this.productFeign = productFeign;
    }

    public OrderResponse getAllOrders(int pageNumber, int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Order> orders = orderRepository.findAll(pageable);

        List<Order> listOfOrders = orders.getContent();

        List<OrderDto> content = listOfOrders.stream().map(this::mapToDTO).collect(Collectors.toList());

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setContent(content);
        orderResponse.setPageNumber(orders.getNumber());
        orderResponse.setPageSize(orders.getSize());
        orderResponse.setTotalElements(orders.getTotalElements());
        orderResponse.setTotalPages(orders.getTotalPages());
        orderResponse.setLast(orders.isLast());

        return orderResponse;
    }

    public OrderDetailsProduct getById(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));

        List<Long> productIds = order.getProducts();

        List<ProductDto> productDto = productFeign.findAllProducts(productIds);

        OrderDetailsProduct orderDetailsProduct = new OrderDetailsProduct();
        orderDetailsProduct.setId(order.getId());
        orderDetailsProduct.setUserId(order.getUserId());
        orderDetailsProduct.setProducts(productDto);
        orderDetailsProduct.setDeliveryAddress(order.getDeliveryAddress());
        orderDetailsProduct.setStatus(order.getStatus());

        return orderDetailsProduct;
    }

    private boolean isValid ;

    @RabbitListener(queues = "confirmation.order-confirm")
    public void receiveMessage(MessageResponse messageResponse) {

        System.out.println(messageResponse);
        isValid = messageResponse.isProductsAvailable();
    }

    public OrderDto createOrder(@Valid OrderRequest orderRequest) {
        try {
        AddressResponse addressResponse = addressFeign.searchAddress(orderRequest.getDeliveryAddress().getZipCode());

        DeliveryAddress deliveryAddress = new DeliveryAddress();
        deliveryAddress.setZipCode(orderRequest.getDeliveryAddress().getZipCode());
        deliveryAddress.setStreet(orderRequest.getDeliveryAddress().getStreet());
        deliveryAddress.setComplement(orderRequest.getDeliveryAddress().getComplement());
        deliveryAddress.setDistrict(orderRequest.getDeliveryAddress().getDistrict());
        deliveryAddress.setCity(addressResponse.getLocalidade());
        deliveryAddress.setState(addressResponse.getUf());

        Order order = new Order();

        order.setId(orderRequest.getId());
        order.setUserId(orderRequest.getUserId());
        order.setProducts(orderRequest.getProducts());
        order.setDeliveryAddress(deliveryAddress);
        order.setStatus(Status.PENDING);

        ProductAvailabilityMessage message = new ProductAvailabilityMessage(order.getProducts());
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, message);

        Thread.sleep(500);

        if (isValid) {
            Order orderResponse = orderRepository.save(order);
            return mapToDTO(orderResponse);
        } else {
            throw new ResourceNotFoundException("Product Not found with Product Microservices",
                    "The specified product(s) were not found in the Product Microservices.");
        }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException e) {
            throw new ResourceNotFoundException("Not Found", e.getMessage());
        }
    }

    public OrderDto updateStatus(Long id, OrderStatus orderStatus) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));

        order.setStatus(orderStatus.getStatus());

        Order orderResponse = orderRepository.save(order);
        return mapToDTO(orderResponse);
    }

    private OrderDto mapToDTO(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setUserId(order.getUserId());
        orderDto.setProducts(order.getProducts());
        orderDto.setDeliveryAddress(order.getDeliveryAddress());
        orderDto.setStatus(order.getStatus());

        return orderDto;
    }

    public void setIsValid(boolean b) {
        isValid = b;
    }
}
