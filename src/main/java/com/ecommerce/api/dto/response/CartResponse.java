package com.ecommerce.api.dto.response;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class CartResponse {
    private UUID id;
    private List<CartItemResponse> items;
    private BigDecimal totalAmount;
    private Integer totalItems;
}