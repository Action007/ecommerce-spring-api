package com.ecommerce.api.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.api.entity.Product;

import jakarta.persistence.LockModeType;

public interface ProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {

    @EntityGraph(attributePaths = { "category" })
    Page<Product> findAll(Pageable pageable);

    @EntityGraph(attributePaths = { "category" })
    Page<Product> findByCategoryId(UUID categoryId, Pageable pageable);

    @EntityGraph(attributePaths = { "category" })
    Page<Product> findAll(Specification<Product> spec, Pageable pageable);

    @EntityGraph(attributePaths = { "category" })
    Page<Product> findByCategoryIdAndIdNot(UUID categoryId, UUID productId, Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Product p WHERE p.id = :id")
    Optional<Product> findByIdWithLock(@Param("id") UUID id);
}