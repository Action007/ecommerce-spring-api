package com.ecommerce.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ecommerce.api.dto.request.ProductRequest;
import com.ecommerce.api.dto.response.ProductResponse;
import com.ecommerce.api.entity.Category;
import com.ecommerce.api.entity.Product;
import com.ecommerce.api.exception.ResourceNotFoundException;
import com.ecommerce.api.mapper.ProductMapper;
import com.ecommerce.api.repository.CategoryRepository;
import com.ecommerce.api.repository.ProductRepository;
import com.ecommerce.api.service.impl.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductMapper productMapper;
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    // Shared test data
    private Product product;
    private Category category;
    private ProductRequest request;
    private ProductResponse response;

    @BeforeEach
    public void setUp() {
        category = new Category();
        category.setId(UUID.randomUUID());
        category.setName("Electronics");

        product = new Product();
        product.setId(UUID.randomUUID());
        product.setName("Laptop");
        product.setPrice(new BigDecimal("999.99"));
        product.setStockQuantity(10);
        product.setCategory(category);
        product.setDeleted(false);

        request = new ProductRequest();
        request.setName("Laptop");
        request.setPrice(new BigDecimal("999.99"));
        request.setStockQuantity(10);
        request.setCategoryId(category.getId());

        response = new ProductResponse();
        response.setId(product.getId());
        response.setName("Laptop");
    }

    // Happy path
    @Test
    public void createProduct_WithValidData_ReturnsProductResponse() {
        // Arrange
        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        when(productMapper.toEntity(request)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toResponse(product)).thenReturn(response);

        // Act
        ProductResponse result = productService.createProduct(request);

        // Assert
        assertNotNull(result);
        assertEquals("Laptop", result.getName());
        verify(productRepository).save(product); // confirms save was called
        verify(productMapper).toResponse(product); // confirms mapping happened
    }

    // Category doesn't exist
    @Test
    public void createProduct_WithNonExistentCategory_ThrowsResourceNotFoundException() {
        when(categoryRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> productService.createProduct(request));

        verify(productRepository, never()).save(any()); // save must NOT be called
    }

    // Soft delete — uses ArgumentCaptor to inspect what was zsaved
    @Test
    public void deleteProduct_WhenExists_SetsDeletedTrueAndSaves() {
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        productService.deleteProduct(product.getId());

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).save(captor.capture());

        assertTrue(captor.getValue().getDeleted());
        assertNotNull(captor.getValue().getDeletedAt());
    }

    // Already deleted — your code checks this
    @Test
    public void deleteProduct_WhenAlreadyDeleted_ThrowsResourceNotFoundException() {
        product.setDeleted(true);
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        assertThrows(ResourceNotFoundException.class,
                () -> productService.deleteProduct(product.getId()));
    }
}
