package com.ecommerce.api.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class OrderItemResponse {
    private UUID productId;
    private String productName;
    private Integer quantity;
    private BigDecimal priceAtPurchase;
    private BigDecimal subtotal;
}