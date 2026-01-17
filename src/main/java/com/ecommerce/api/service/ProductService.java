package com.ecommerce.api.service;

import java.math.BigDecimal;
import java.util.UUID;

import com.ecommerce.api.dto.request.ProductRequest;
import com.ecommerce.api.dto.response.PageResponse;
import com.ecommerce.api.dto.response.ProductResponse;

public interface ProductService {
    public ProductResponse createProduct(ProductRequest request);

    public ProductResponse updateProduct(UUID id, ProductRequest request);

    public void deleteProduct(UUID id);

    public PageResponse<ProductResponse> searchProducts(
            String name,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            UUID categoryId,
            int page,
            int size,
            String sortBy,
            String sortDir);

    public ProductResponse getProductById(UUID id);

    public void updateProductRating(UUID productId, int newRating);

    public PageResponse<ProductResponse> getRelatedProducts(UUID productId, int page, int size);
}
