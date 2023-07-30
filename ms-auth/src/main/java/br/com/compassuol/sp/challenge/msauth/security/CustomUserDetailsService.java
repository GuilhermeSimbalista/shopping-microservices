package br.com.compassuol.sp.challenge.msauth.security;

import br.com.compassuol.sp.challenge.msauth.entities.User;
import br.com.compassuol.sp.challenge.msauth.feignClients.FeignConfig;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Collections;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final FeignConfig feignConfig;

    public CustomUserDetailsService(FeignConfig feignConfig){
        this.feignConfig = feignConfig;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = feignConfig.getByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username:"));
        SimpleGrantedAuthority authorities = new SimpleGrantedAuthority(user.getRole());
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singleton(authorities));
    }
}
