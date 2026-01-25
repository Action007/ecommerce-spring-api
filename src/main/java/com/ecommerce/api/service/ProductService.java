package com.ecommerce.api.service;

import java.math.BigDecimal;
import java.util.UUID;

import com.ecommerce.api.dto.request.ProductRequest;
import com.ecommerce.api.dto.response.PageResponse;
import com.ecommerce.api.dto.response.ProductResponse;

public interface ProductService {
    ProductResponse createProduct(ProductRequest request);

    ProductResponse updateProduct(UUID id, ProductRequest request);

    void deleteProduct(UUID id);

    PageResponse<ProductResponse> searchProducts(
            String name,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            UUID categoryId,
            int page,
            int size,
            String sortBy,
            String sortDir);

    ProductResponse getProductById(UUID id);

    void updateProductRating(UUID productId, int newRating);

    PageResponse<ProductResponse> getRelatedProducts(UUID productId, int page, int size);
}
