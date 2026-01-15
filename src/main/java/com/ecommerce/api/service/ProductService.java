package com.ecommerce.api.service;

import java.util.List;
import java.util.UUID;

import com.ecommerce.api.dto.request.ProductRequest;
import com.ecommerce.api.dto.response.ProductResponse;

public interface ProductService {
    public List<ProductResponse> getAllProductsWithCategory();

    public ProductResponse createProduct(ProductRequest request);

    public ProductResponse updateProduct(UUID id, ProductRequest request);

    public void deleteProduct(UUID id);
}
