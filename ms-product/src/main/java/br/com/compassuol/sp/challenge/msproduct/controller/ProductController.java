package br.com.compassuol.sp.challenge.msproduct.controller;

import br.com.compassuol.sp.challenge.msproduct.payload.ProductDto;
import br.com.compassuol.sp.challenge.msproduct.payload.ProductResponse;
import br.com.compassuol.sp.challenge.msproduct.services.ProductService;
import br.com.compassuol.sp.challenge.msproduct.utils.ProductConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
@Tag(name = "Product Controller")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(
            summary = "Create Product REST API",
            description = "Create Product REST API is used to save product into database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        ProductDto productResponse = productService.createProduct(productDto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(productResponse.getId()).toUri();

        return ResponseEntity.created(uri).body(productResponse);
    }

    @Operation(
            summary = "Get All Products REST API",
            description = "Get All Products REST API is used to get all products into database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @GetMapping
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam(value = "pageNumber", defaultValue = ProductConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = ProductConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize
    ) {
        ProductResponse products = productService.getAllProducts(pageNumber, pageSize);
        return ResponseEntity.ok(products);
    }

    @Operation(
            summary = "Get Products by ids REST API",
            description = "Get Products by ids REST API is used to get products into database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @GetMapping("/ids")
    public ResponseEntity<List<ProductDto>> getAllById(@RequestParam("ids") List<Long> ids) {
        List<ProductDto> productDto = productService.getAllById(ids);
        return ResponseEntity.ok(productDto);
    }

    @Operation(
            summary = "Update Product REST API",
            description = "Update Product REST API is used to update product into database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        ProductDto productResponse = productService.updateProduct(id, productDto);
        return ResponseEntity.ok(productResponse);
    }

    @Operation(
            summary = "Delete Product REST API",
            description = "Delete Product REST API is used to delete product by id into database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().body("Product id: " + id + " deleted successfully!");
    }
}
