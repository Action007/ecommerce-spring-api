package com.ecommerce.api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.api.entity.Product;

public interface ProductRepository extends JpaRepository<Product, UUID> {

}
