package br.com.compassuol.sp.challenge.msorder;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Order Microservice",
				description = "Order Microservice Documentation",
				version = "v1.0",
				contact = @Contact(
						name = "Guilherme Simbalista",
						email = "guilherme.schelba.pb@compasso.com.br"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Order Microservice Source Code",
				url = "https://github.com/GuilhermeSimbalista/CompassUOL_SP_Challenge03_BIOS_Guilherme_Simbalista/tree/develop/ms-order"
		)
)
public class MsOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsOrderApplication.class, args);
	}

}
