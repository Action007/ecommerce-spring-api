package com.ecommerce.api.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.api.entity.Product;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query("SELECT p FROM Product p JOIN FETCH p.category")
    List<Product> findAllWithCategory();
}
