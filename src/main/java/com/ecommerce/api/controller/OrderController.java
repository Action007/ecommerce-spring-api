package com.ecommerce.api.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.api.dto.request.PlaceOrderRequest;
import com.ecommerce.api.dto.response.OrderResponse;
import com.ecommerce.api.dto.response.PageResponse;
import com.ecommerce.api.entity.OrderStatus;
import com.ecommerce.api.service.OrderService;
import com.ecommerce.api.util.SecurityUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(@Valid @RequestBody PlaceOrderRequest request) {
        UUID userId = SecurityUtil.getCurrentUserId();
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.placeOrder(userId, request));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable UUID orderId) {
        UUID userId = SecurityUtil.getCurrentUserId();
        return ResponseEntity.ok(orderService.getOrderById(orderId, userId));
    }

    @GetMapping
    public ResponseEntity<PageResponse<OrderResponse>> getMyOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        UUID userId = SecurityUtil.getCurrentUserId();
        return ResponseEntity.ok(orderService.getMyOrders(userId, page, size));
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable UUID orderId) {
        UUID userId = SecurityUtil.getCurrentUserId();
        orderService.cancelOrder(orderId, userId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @PathVariable UUID orderId,
            @RequestParam OrderStatus status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, status));
    }
}