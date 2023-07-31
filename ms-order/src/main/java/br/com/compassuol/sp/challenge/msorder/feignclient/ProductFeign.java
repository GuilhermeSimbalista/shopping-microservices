package br.com.compassuol.sp.challenge.msorder.feignclient;

import br.com.compassuol.sp.challenge.msorder.payload.AddressResponse;
import br.com.compassuol.sp.challenge.msorder.payload.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "ms-product", url = "ms-product:8081", path = "/products")
public interface ProductFeign {

    @GetMapping("/ids")
    List<ProductDto> findAllProducts(@RequestParam("ids")List<Long>ids);
}
