package br.com.compassuol.sp.challenge.apigateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.util.StringUtils;

@Configuration
public class GatewayConfig {


    private final TokenValidationService tokenValidationService;

    @Autowired
    public GatewayConfig(TokenValidationService tokenValidationService) {
        this.tokenValidationService = tokenValidationService;
    }

    @Value("${ms.auth.url}")
    private String authBaseUrl;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("ms-auth", r -> r.path("/auth/**")
                        .filters(f -> f.filter(authenticationFilter()))
                        .uri("http://localhost:8083"))
                .route("ms-product", r -> r.path("/products/**")
                        .filters(f -> f.filter(authenticationFilter()))
                        .uri("http://localhost:8081"))
                .route("ms-order", r -> r.path("/orders/**")
                        .filters(f -> f.filter(authenticationFilter()))
                        .uri("http://localhost:8082"))
                .route("ms-user", r -> r.path("/users/**")
                        .filters(f -> f.filter(authenticationFilter()))
                        .uri("http://localhost:8084"))
                .route("block-others", r -> r.path("/**")
                        .filters(f -> f.filter(authenticationFilter()))
                        .uri("http://localhost:8080"))


                .build();
    }
    private GatewayFilter authenticationFilter() {
   return (exchange, chain) -> {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().value();
        if (path.startsWith("/auth/login")) {
            return chain.filter(exchange);
        }

       String token = getTokenFromRequest(request);
       if (StringUtils.hasText(token) && tokenValidationService.validateToken(token)) {
           ServerHttpRequest modifiedRequest = new ServerHttpRequestDecorator(request) {
               @Override
               public HttpHeaders getHeaders() {
                   HttpHeaders headers = new HttpHeaders();
                   headers.putAll(super.getHeaders());
                   headers.remove(HttpHeaders.AUTHORIZATION);
                   return headers;
               }
           };
           return chain.filter(exchange.mutate().request(modifiedRequest).build());
       } else {
           exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
           return exchange.getResponse().setComplete();
       }
   };
    }

    private String getTokenFromRequest(ServerHttpRequest request) {
        String bearerToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}


