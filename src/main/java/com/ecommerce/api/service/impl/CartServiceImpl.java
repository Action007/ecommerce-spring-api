package com.ecommerce.api.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.api.dto.response.CartItemResponse;
import com.ecommerce.api.dto.response.CartResponse;
import com.ecommerce.api.entity.Cart;
import com.ecommerce.api.entity.CartItem;
import com.ecommerce.api.entity.Product;
import com.ecommerce.api.entity.User;
import com.ecommerce.api.exception.BusinessException;
import com.ecommerce.api.exception.ResourceNotFoundException;
import com.ecommerce.api.repository.CartItemRepository;
import com.ecommerce.api.repository.CartRepository;
import com.ecommerce.api.repository.ProductRepository;
import com.ecommerce.api.repository.UserRepository;
import com.ecommerce.api.service.CartService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public CartResponse addToCart(UUID userId, UUID productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (product.getStockQuantity() < quantity) {
            throw new BusinessException("Insufficient stock");
        }

        Cart cart = findOrCreateCart(userId);

        Optional<CartItem> existingItem = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), productId);

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            int newQuantity = item.getQuantity() + quantity;

            if (product.getStockQuantity() < newQuantity) {
                throw new BusinessException("Insufficient stock for requested quantity");
            }

            item.setQuantity(newQuantity);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setPriceAtAdd(product.getPrice());

            cart.getItems().add(cartItem);
        }

        cartRepository.save(cart);
        return mapToCartResponse(cart);
    }

    @Override
    @Transactional
    public CartResponse updateQuantity(UUID userId, UUID productId, int quantity) {
        Cart cart = getCartByUserId(userId);

        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found in cart"));

        if (quantity <= 0) {
            cart.getItems().remove(cartItem);
        } else {
            Product product = cartItem.getProduct();
            if (product.getStockQuantity() < quantity) {
                throw new BusinessException("Insufficient stock");
            }
            cartItem.setQuantity(quantity);
        }

        cartRepository.save(cart);
        return mapToCartResponse(cart);
    }

    @Override
    @Transactional
    public void removeFromCart(UUID userId, UUID productId) {
        Cart cart = getCartByUserId(userId);

        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found in cart"));

        cart.getItems().remove(cartItem);
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void clearCart(UUID userId) {
        Cart cart = getCartByUserId(userId);

        cart.getItems().clear();
        cartRepository.save(cart);
    }

    @Override
    @Transactional(readOnly = true)
    public CartResponse getCart(UUID userId) {
        Cart cart = getCartByUserId(userId);
        return mapToCartResponse(cart);
    }

    private Cart getCartByUserId(UUID userId) {
        return cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
    }

    private Cart findOrCreateCart(UUID userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return newCart;
                });
    }

    private CartResponse mapToCartResponse(Cart cart) {
        List<CartItemResponse> items = cart.getItems().stream()
                .map(item -> CartItemResponse.builder()
                        .productId(item.getProduct().getId())
                        .productName(item.getProduct().getName())
                        .quantity(item.getQuantity())
                        .priceAtAdd(item.getPriceAtAdd())
                        .subtotal(item.getPriceAtAdd()
                                .multiply(BigDecimal.valueOf(item.getQuantity())))
                        .build())
                .toList();

        BigDecimal total = items.stream()
                .map(CartItemResponse::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return CartResponse.builder()
                .id(cart.getId())
                .items(items)
                .totalAmount(total)
                .totalItems(items.size())
                .build();
    }
}
