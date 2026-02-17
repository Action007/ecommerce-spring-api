package com.ecommerce.api.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.api.entity.Order;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    Page<Order> findByUserId(UUID userId, Pageable pageable);
}