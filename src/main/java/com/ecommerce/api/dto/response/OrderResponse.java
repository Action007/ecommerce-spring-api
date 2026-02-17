package com.ecommerce.api.dto.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class OrderResponse {
    private UUID id;
    private String orderNumber;
    private String status;
    private List<OrderItemResponse> items;
    private BigDecimal totalAmount;
    private String shippingAddress;
    private String paymentMethod;
    private Instant paidAt;
    private Instant createdAt;
}