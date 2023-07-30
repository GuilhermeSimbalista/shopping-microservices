package br.com.compassuol.sp.challenge.msauth.feignClients;

import br.com.compassuol.sp.challenge.msauth.entities.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Component
@FeignClient(name = "ms-user", url = "localhost:8084", path = "/users")
public interface FeignConfig {

    @GetMapping(value = "/search")
    Optional<User> getByUsername(@RequestParam String username);
}
