package br.com.compassuol.sp.challenge.msproduct.service;

import br.com.compassuol.sp.challenge.msproduct.entities.Product;
import br.com.compassuol.sp.challenge.msproduct.payload.ProductDto;
import br.com.compassuol.sp.challenge.msproduct.payload.ProductResponse;
import br.com.compassuol.sp.challenge.msproduct.repositories.ProductRepository;
import br.com.compassuol.sp.challenge.msproduct.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateProduct() {
        // Mocking input data
        ProductDto productDto = new ProductDto();
        productDto.setName("Test Product");
        productDto.setDescription("Test Description");
        productDto.setPrice(BigDecimal.valueOf(100.0));

        // Mocking the behavior of the productRepository.save() method
        Product savedProduct = new Product();
        savedProduct.setId(1L);
        savedProduct.setName("Test Product");
        savedProduct.setDescription("Test Description");
        savedProduct.setPrice(BigDecimal.valueOf(100.0));
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        // Call the method
        ProductDto result = productService.createProduct(productDto);

        // Assertions
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Product", result.getName());
        assertEquals("Test Description", result.getDescription());
        assertEquals(BigDecimal.valueOf(100.0), result.getPrice());
    }

    @Test
    public void testGetAllProducts() {
        int pageNumber = 0;
        int pageSize = 10;

        List<Product> products = new ArrayList<>();
        products.add(new Product(1L, "Product 1", "Description 1", BigDecimal.valueOf(50.0)));
        products.add(new Product(2L, "Product 2", "Description 2", BigDecimal.valueOf(60.0)));
        Page<Product> page = new PageImpl<>(products, PageRequest.of(pageNumber, pageSize), products.size());
        when(productRepository.findAll(any(PageRequest.class))).thenReturn(page);

        ProductResponse result = productService.getAllProducts(pageNumber, pageSize);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals(0, result.getPageNumber());
        assertEquals(pageSize, result.getPageSize());
        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
    }

    @Test
    public void testUpdateProduct() {
        Long productId = 1L;
        ProductDto productDto = new ProductDto();
        productDto.setName("Updated Product");
        productDto.setDescription("Updated Description");
        productDto.setPrice(BigDecimal.valueOf(100.0));

        Product existingProduct = new Product(productId, "Product 1", "Description 1", BigDecimal.valueOf(200.0));
        when(productRepository.findById(productId)).thenReturn(java.util.Optional.of(existingProduct));

        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);

        ProductDto updatedProductDto = productService.updateProduct(productId, productDto);

        assertNotNull(updatedProductDto);
        assertEquals(productId, updatedProductDto.getId());
        assertEquals(productDto.getName(), updatedProductDto.getName());
        assertEquals(productDto.getDescription(), updatedProductDto.getDescription());
        assertEquals(productDto.getPrice(), updatedProductDto.getPrice());
    }

    @Test
    public void testDeleteProduct() {
        Long productId = 1L;

        Product existingProduct = new Product(productId, "Product 1", "Description 1", BigDecimal.valueOf(100.0));
        when(productRepository.findById(productId)).thenReturn(java.util.Optional.of(existingProduct));

        productService.deleteProduct(productId);

        verify(productRepository, times(1)).delete(existingProduct);
    }

    @Test
    public void testGetAllByIdWithValidIds() {
        List<Long> ids = Arrays.asList(1L, 2L, 3L);
        Product product1 = new Product(1L, "Product 1", "Description 1", BigDecimal.valueOf(50.0));
        Product product2 = new Product(2L, "Product 2", "Description 2", BigDecimal.valueOf(60.0));
        Product product3 = new Product(3L, "Product 3", "Description 3", BigDecimal.valueOf(70.0));
        when(productRepository.findAllById(ids)).thenReturn(Arrays.asList(product1, product2, product3));

        List<ProductDto> result = productService.getAllById(ids);

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("Product 1", result.get(0).getName());
        assertEquals("Product 2", result.get(1).getName());
        assertEquals("Product 3", result.get(2).getName());
    }

    @Test
    public void testGetAllByIdWithEmptyList() {
        List<Long> ids = Arrays.asList();
        when(productRepository.findAllById(ids)).thenReturn(Arrays.asList());

        List<ProductDto> result = productService.getAllById(ids);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }


}


