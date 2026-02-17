package com.ecommerce.api.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class CartItemRequest {
    @NotNull
    private UUID productId;

    @NotNull
    @Min(1)
    private Integer quantity;
}