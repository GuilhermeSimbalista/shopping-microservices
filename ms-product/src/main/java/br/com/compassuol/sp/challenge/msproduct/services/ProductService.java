package br.com.compassuol.sp.challenge.msproduct.services;

import br.com.compassuol.sp.challenge.msproduct.entities.Product;
import br.com.compassuol.sp.challenge.msproduct.exceptions.ResourceNotFoundException;
import br.com.compassuol.sp.challenge.msproduct.payload.ProductDto;
import br.com.compassuol.sp.challenge.msproduct.payload.ProductResponse;
import br.com.compassuol.sp.challenge.msproduct.repositories.ProductRepository;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDto createProduct(@Valid ProductDto productDto) {
        Product product = mapToEntity(productDto);
        Product newProduct = productRepository.save(product);

        return mapToDTO(newProduct);
    }

    public ProductResponse getAllProducts(int pageNumber, int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Product> products = productRepository.findAll(pageable);

        List<Product> listOfProducts = products.getContent();

        List<ProductDto> content = listOfProducts.stream().map(this::mapToDTO).collect(Collectors.toList());


        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(content);
        productResponse.setPageNumber(products.getNumber());
        productResponse.setPageSize(products.getSize());
        productResponse.setTotalElements(products.getTotalElements());
        productResponse.setTotalPages(products.getTotalPages());
        productResponse.setLast(products.isLast());

        return productResponse;
    }

    public List<ProductDto> getAllById(List<Long> ids) {
        List<Product> products = productRepository.findAllById(ids);

        return products.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product product = findById(id);

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());

        Product updatedProduct = productRepository.save(product);

        return mapToDTO(updatedProduct);
    }

    public void deleteProduct(Long id) {
        Product product = findById(id);
        productRepository.delete(product);
    }

    private Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
    }

    public boolean containsTo(List<Long> list1, List<Long> list2) {
        return new HashSet<>(list1).containsAll(list2);
    }

    private ProductDto mapToDTO(Product product) {
            ProductDto productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setName(product.getName());
            productDto.setDescription(product.getDescription());
            productDto.setPrice(product.getPrice());

            return productDto;
    }

    private Product mapToEntity(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());

        return product;
    }
}





