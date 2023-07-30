package br.com.compassuol.sp.challenge.msorder.consumers;



import br.com.compassuol.sp.challenge.msorder.config.RabbitMQConfig;
import br.com.compassuol.sp.challenge.msorder.payload.MessageResponse;
import br.com.compassuol.sp.challenge.msorder.payload.ProductDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductAvailabilityConsumer {

    private final RabbitTemplate rabbitTemplate;

    public ProductAvailabilityConsumer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    //@RabbitListener(queues = "confirmation.order-confirm")
    //public void receiveMessage(MessageResponse messageResponse) {

        //System.out.println(messageResponse);
    }
//}
