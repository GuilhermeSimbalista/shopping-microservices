package br.com.compassuol.sp.challenge.msauth.services;

import br.com.compassuol.sp.challenge.msauth.entities.User;
import br.com.compassuol.sp.challenge.msauth.exceptions.ResourceNotFoundException;
import br.com.compassuol.sp.challenge.msauth.feignClients.FeignConfig;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final FeignConfig feignConfig;


    public UserService(FeignConfig feignConfig) {
        this.feignConfig = feignConfig;
    }

    public User getByUsername(String username) {
        return feignConfig.getByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User", "Username", username));
    }
}
