package br.com.compassuol.sp.challenge.msproduct.controller;

import br.com.compassuol.sp.challenge.msproduct.payload.ProductDto;
import br.com.compassuol.sp.challenge.msproduct.payload.ProductResponse;
import br.com.compassuol.sp.challenge.msproduct.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

public class ProductControllerTest {

    private ProductService productService;
    private ProductController productController;

    @BeforeEach
    public void setUp() {
        productService = mock(ProductService.class);
        productController = new ProductController(productService);
    }

    @Test
    public void testGetAllProducts() {
        // Given
        int pageNumber = 0;
        int pageSize = 10;

        ProductDto productDto1 = new ProductDto();
        productDto1.setId(1L);
        productDto1.setName("Product 1");
        productDto1.setDescription("Description 1");
        productDto1.setPrice(BigDecimal.valueOf(19.99));

        ProductDto productDto2 = new ProductDto();
        productDto2.setId(2L);
        productDto2.setName("Product 2");
        productDto2.setDescription("Description 2");
        productDto2.setPrice(BigDecimal.valueOf(29.99));

        List<ProductDto> productList = Arrays.asList(productDto1, productDto2);
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productList);
        productResponse.setPageNumber(pageNumber);
        productResponse.setPageSize(pageSize);
        productResponse.setTotalElements(2L);
        productResponse.setTotalPages(1);
        productResponse.setLast(true);

        when(productService.getAllProducts(anyInt(), anyInt())).thenReturn(productResponse);

        // When
        ResponseEntity<ProductResponse> response = productController.getAllProducts(pageNumber, pageSize);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(productResponse, response.getBody());

        verify(productService, times(1)).getAllProducts(pageNumber, pageSize);
    }

    @Test
    public void testGetAllById() {
        // Given
        List<Long> ids = Arrays.asList(1L, 2L, 3L);

        ProductDto productDto1 = new ProductDto();
        productDto1.setId(1L);
        productDto1.setName("Product 1");
        productDto1.setDescription("Description 1");
        productDto1.setPrice(BigDecimal.valueOf(19.99));

        ProductDto productDto2 = new ProductDto();
        productDto2.setId(2L);
        productDto2.setName("Product 2");
        productDto2.setDescription("Description 2");
        productDto2.setPrice(BigDecimal.valueOf(29.99));

        List<ProductDto> productList = Arrays.asList(productDto1, productDto2);

        when(productService.getAllById(anyList())).thenReturn(productList);

        // When
        ResponseEntity<List<ProductDto>> response = productController.getAllById(ids);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(productList, response.getBody());

        verify(productService, times(1)).getAllById(ids);
    }

    @Test
    public void testUpdateProduct() {
        // Given
        Long id = 1L;

        ProductDto productDto = new ProductDto();
        productDto.setName("Updated Product");
        productDto.setDescription("Updated Description");
        productDto.setPrice(BigDecimal.valueOf(39.99));

        ProductDto updatedProductDto = new ProductDto();
        updatedProductDto.setId(id);
        updatedProductDto.setName(productDto.getName());
        updatedProductDto.setDescription(productDto.getDescription());
        updatedProductDto.setPrice(productDto.getPrice());

        when(productService.updateProduct(anyLong(), any(ProductDto.class))).thenReturn(updatedProductDto);

        // When
        ResponseEntity<ProductDto> response = productController.updateProduct(id, productDto);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(updatedProductDto, response.getBody());

        verify(productService, times(1)).updateProduct(id, productDto);
    }

    @Test
    public void testDeleteProduct() {
        // Given
        Long id = 1L;

        // When
        ResponseEntity<String> response = productController.deleteProduct(id);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("Product id: 1 deleted successfully!"));

        verify(productService, times(1)).deleteProduct(id);
    }
}
