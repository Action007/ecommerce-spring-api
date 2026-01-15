package com.ecommerce.api.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.api.dto.request.CategoryRequest;
import com.ecommerce.api.dto.response.CategoryResponse;
import com.ecommerce.api.entity.Category;
import com.ecommerce.api.exception.DuplicateResourceException;
import com.ecommerce.api.exception.ResourceNotFoundException;
import com.ecommerce.api.mapper.CategoryMapper;
import com.ecommerce.api.repository.CategoryRepository;
import com.ecommerce.api.service.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {

        if (categoryRepository.existsByName(categoryRequest.getName())) {
            throw new DuplicateResourceException(
                    "Category with this name already exists");
        }

        Category category = categoryMapper.toEntity(categoryRequest);

        if (categoryRequest.getParentId() != null) {
            Category parent = categoryRepository.findById(categoryRequest.getParentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent category not found"));

            category.setParent(parent);
        }

        categoryRepository.save(category);

        return categoryMapper.toResponse(category);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        List<CategoryResponse> categories = categoryRepository.findByParentIsNull().stream()
                .map((category) -> categoryMapper.toResponse(category)).toList();
        return categories;
    }

    @Override
    public CategoryResponse getCategoryWithChildren(UUID id) {
        Category category = categoryRepository.findByIdWithChildren(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        return categoryMapper.toResponse(category);
    }

}
