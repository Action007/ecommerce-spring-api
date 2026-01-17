package com.ecommerce.api.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ecommerce.api.dto.request.ProductRequest;
import com.ecommerce.api.dto.response.PageResponse;
import com.ecommerce.api.dto.response.ProductResponse;
import com.ecommerce.api.entity.Category;
import com.ecommerce.api.entity.Product;
import com.ecommerce.api.exception.ResourceNotFoundException;
import com.ecommerce.api.mapper.ProductMapper;
import com.ecommerce.api.repository.CategoryRepository;
import com.ecommerce.api.repository.ProductRepository;
import com.ecommerce.api.service.ProductService;
import com.ecommerce.api.specification.ProductSpecification;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

        private final ProductRepository productRepository;
        private final ProductMapper productMapper;
        private final CategoryRepository categoryRepository;

        @Override
        public PageResponse<ProductResponse> searchProducts(String name, BigDecimal minPrice, BigDecimal maxPrice,
                        UUID categoryId, int page, int size, String sortBy, String sortDir) {
                Specification<Product> spec = Specification.where(ProductSpecification.hasName(name))
                                .and(ProductSpecification.hasPriceGreaterThan(minPrice))
                                .and(ProductSpecification.hasPriceLessThan(maxPrice))
                                .and(ProductSpecification.hasCategory(categoryId));

                Sort sort = sortDir.equalsIgnoreCase("asc")
                                ? Sort.by(sortBy).ascending()
                                : Sort.by(sortBy).descending();
                Pageable pageable = PageRequest.of(page, size, sort);

                Page<Product> productPage = productRepository.findAll(spec, pageable);
                Page<ProductResponse> responsePage = productPage.map(productMapper::toResponse);

                return PageResponse.of(responsePage);
        }

        @Override
        @Transactional
        public ProductResponse createProduct(ProductRequest request) {
                Category category = categoryRepository.findById(request.getCategoryId())
                                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

                Product product = productMapper.toEntity(request);
                product.setCategory(category);

                Product savedProduct = productRepository.save(product);

                return productMapper.toResponse(savedProduct);
        }

        @Override
        @Transactional
        public ProductResponse updateProduct(UUID id, ProductRequest request) {
                Product product = productRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

                Category category = categoryRepository.findById(request.getCategoryId())
                                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

                product.setName(request.getName());
                product.setDescription(request.getDescription());
                product.setPrice(request.getPrice());
                product.setStockQuantity(request.getStockQuantity());
                product.setCategory(category);

                Product updatedProduct = productRepository.save(product);
                return productMapper.toResponse(updatedProduct);
        }

        @Override
        @Transactional
        public void deleteProduct(UUID id) {
                Product product = productRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

                product.setDeleted(true);
                product.setDeletedAt(Instant.now());
                productRepository.save(product);
        }

        @Override
        public ProductResponse getProductById(UUID id) {
                Product product = productRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

                return productMapper.toResponse(product);
        }

        @Override
        @Transactional
        public void updateProductRating(UUID productId, int newRating) {
                Product product = productRepository.findById(productId)
                                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

                BigDecimal currentTotal = product.getAverageRating()
                                .multiply(BigDecimal.valueOf(product.getRatingCount()));
                BigDecimal newTotal = currentTotal.add(BigDecimal.valueOf(newRating));
                int newCount = product.getRatingCount() + 1;

                product.setAverageRating(newTotal.divide(BigDecimal.valueOf(newCount), 2, RoundingMode.HALF_UP));
                product.setRatingCount(newCount);

                productRepository.save(product);
        }

        public PageResponse<ProductResponse> getRelatedProducts(UUID productId, int page, int size) {
                Product product = productRepository.findById(productId)
                                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

                Pageable pageable = PageRequest.of(page, size);
                Page<Product> relatedProducts = productRepository.findByCategoryIdAndIdNot(
                                product.getCategory().getId(),
                                productId,
                                pageable);

                return PageResponse.of(relatedProducts.map(productMapper::toResponse));
        }
}
