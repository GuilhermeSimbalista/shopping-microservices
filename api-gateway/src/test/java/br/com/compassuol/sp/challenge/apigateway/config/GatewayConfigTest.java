package br.com.compassuol.sp.challenge.apigateway.config;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class GatewayConfigTest {

    @Mock
    private TokenValidationService tokenValidationService;

    @InjectMocks
    private GatewayConfig gatewayConfig;

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        // Configurar o comportamento do mock conforme necessário
        // Por exemplo, para um token válido:
        when(tokenValidationService.validateToken("valid-token")).thenReturn(true);
        // Para um token inválido:
        when(tokenValidationService.validateToken("invalid-token")).thenReturn(false);
    }

    @Test
    public void testOtherRoutesWithInvalidToken() {
        String invalidToken = "invalid-token"; // Token inválido

        webTestClient.get().uri("/products/somepath")
                .header("Authorization", "Bearer " + invalidToken)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.UNAUTHORIZED);

        webTestClient.post().uri("/orders/somepath")
                .header("Authorization", "Bearer " + invalidToken)
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.UNAUTHORIZED);

        // Mais testes para outras rotas
    }
}

