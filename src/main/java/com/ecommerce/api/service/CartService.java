package com.ecommerce.api.service;

import java.util.UUID;

import com.ecommerce.api.dto.response.CartResponse;

public interface CartService {

    CartResponse addToCart(UUID userId, UUID productId, int quantity);

    CartResponse updateQuantity(UUID userId, UUID productId, int quantity);

    void removeFromCart(UUID userId, UUID productId);

    void clearCart(UUID userId);

    CartResponse getCart(UUID userId);
}
