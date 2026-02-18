package com.ecommerce.api.dto.request;

import com.ecommerce.api.entity.OrderStatus;

import lombok.Data;

@Data
public class UpdateOrderStatusRequest {
    private OrderStatus status;
}
