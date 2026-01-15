package com.ecommerce.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ecommerce.api.dto.request.ProductRequest;
import com.ecommerce.api.dto.response.ProductResponse;
import com.ecommerce.api.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "categoryName", source = "category.name")
    ProductResponse toResponse(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "category", ignore = true)
    Product toEntity(ProductRequest request);
}