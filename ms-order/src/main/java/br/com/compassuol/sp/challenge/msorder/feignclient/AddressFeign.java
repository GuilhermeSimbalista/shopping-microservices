package br.com.compassuol.sp.challenge.msorder.feignclient;

import br.com.compassuol.sp.challenge.msorder.payload.AddressResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "https://viacep.com.br/ws/", name = "viacep")
public interface AddressFeign {

    @GetMapping("{cep}/json")
    AddressResponse searchAddress(@PathVariable("cep") String cep);

}
