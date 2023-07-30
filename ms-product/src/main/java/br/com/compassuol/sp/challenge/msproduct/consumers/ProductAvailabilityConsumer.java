package br.com.compassuol.sp.challenge.msproduct.consumers;


import br.com.compassuol.sp.challenge.msproduct.config.RabbitMQConfig;
import br.com.compassuol.sp.challenge.msproduct.payload.MessageResponse;
import br.com.compassuol.sp.challenge.msproduct.payload.ProductDto;
import br.com.compassuol.sp.challenge.msproduct.payload.ReceiveProducts;
import br.com.compassuol.sp.challenge.msproduct.services.ProductService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductAvailabilityConsumer {

    private final ProductService productService;
    private final RabbitTemplate rabbitTemplate;

    public ProductAvailabilityConsumer(ProductService productService, RabbitTemplate rabbitTemplate) {
        this.productService = productService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "order-confirm")
    public void receiveMessage(ReceiveProducts receiveProducts) {

        MessageResponse messageResponse = new MessageResponse();

        List<Long> list = new ArrayList<>(receiveProducts.getProducts());

        List<ProductDto> productDto =  productService.getAllById(list);
        messageResponse.setProducts(productDto);

        List<Long> listToCompare = productDto.stream().map(ProductDto::getId).toList();
        boolean isContains = productService.containsTo(listToCompare, list);

        messageResponse.setProductsAvailable(isContains);

        rabbitTemplate.convertAndSend(RabbitMQConfig.CONFIRMATION_EXCHANGE_NAME, RabbitMQConfig.CONFIRMATION_ROUTING_KEY, messageResponse);
    }
}
