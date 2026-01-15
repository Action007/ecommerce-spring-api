package com.ecommerce.api.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.api.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    boolean existsByName(String name);

    List<Category> findByParentIsNull();

    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.children WHERE c.id = :id")
    Optional<Category> findByIdWithChildren(@Param("id") UUID id);
}
