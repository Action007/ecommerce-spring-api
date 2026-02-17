package com.ecommerce.api.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class CartItemResponse {
    private UUID productId;
    private String productName;
    private Integer quantity;
    private BigDecimal priceAtAdd;
    private BigDecimal subtotal;
}