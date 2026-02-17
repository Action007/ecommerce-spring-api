package com.ecommerce.api.service;

import java.util.UUID;

import com.ecommerce.api.dto.request.PlaceOrderRequest;
import com.ecommerce.api.dto.response.OrderResponse;
import com.ecommerce.api.dto.response.PageResponse;
import com.ecommerce.api.entity.OrderStatus;

public interface OrderService {
    OrderResponse placeOrder(UUID userId, PlaceOrderRequest request);

    OrderResponse getOrderById(UUID orderId, UUID userId);

    PageResponse<OrderResponse> getMyOrders(UUID userId, int page, int size);

    void cancelOrder(UUID orderId, UUID userId);

    OrderResponse updateOrderStatus(UUID orderId, OrderStatus newStatus); // admin only
}