package com.ecommerce.api.service;

import java.util.List;
import java.util.UUID;

import com.ecommerce.api.dto.request.CategoryRequest;
import com.ecommerce.api.dto.response.CategoryResponse;

public class CategoryServiceImp implements CategoryService {

    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {

    }

    @Override
    public List<CategoryResponse> getAllCategories() {
    }

    @Override
    public CategoryResponse getCategoryWithChildren(UUID id) {
    }

}
