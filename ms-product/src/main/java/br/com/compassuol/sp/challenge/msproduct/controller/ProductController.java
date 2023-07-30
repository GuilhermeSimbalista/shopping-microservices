package br.com.compassuol.sp.challenge.msproduct.controller;

import br.com.compassuol.sp.challenge.msproduct.payload.ProductDto;
import br.com.compassuol.sp.challenge.msproduct.payload.ProductResponse;
import br.com.compassuol.sp.challenge.msproduct.services.ProductService;
import br.com.compassuol.sp.challenge.msproduct.utils.ProductConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        ProductDto productResponse = productService.createProduct(productDto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(productResponse.getId()).toUri();

        return ResponseEntity.created(uri).body(productResponse);
    }

    @GetMapping
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam(value = "pageNumber", defaultValue = ProductConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = ProductConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize
    ) {
        ProductResponse products = productService.getAllProducts(pageNumber, pageSize);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/ids")
    public ResponseEntity<List<ProductDto>> getAllById(@RequestParam("ids") List<Long> ids) {
        List<ProductDto> productDto = productService.getAllById(ids);
        return ResponseEntity.ok(productDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        ProductDto productResponse = productService.updateProduct(id, productDto);
        return ResponseEntity.ok(productResponse);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().body("Product id: " + id + " deleted successfully!");
    }
}
