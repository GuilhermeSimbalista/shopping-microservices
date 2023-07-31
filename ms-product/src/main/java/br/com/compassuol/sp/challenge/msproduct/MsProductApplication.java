package br.com.compassuol.sp.challenge.msproduct;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Product Microservice",
				description = "Product Microservice Documentation",
				version = "v1.0",
				contact = @Contact(
						name = "Guilherme Simbalista",
						email = "guilherme.schelba.pb@compasso.com.br"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Product Microservice Source Code",
				url = "https://github.com/GuilhermeSimbalista/CompassUOL_SP_Challenge03_BIOS_Guilherme_Simbalista/tree/develop/ms-product"
		)
)
public class MsProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsProductApplication.class, args);
	}

}
