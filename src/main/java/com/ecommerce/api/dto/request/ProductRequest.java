package com.ecommerce.api.dto.request;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductRequest {
    @NotBlank
    private String name;

    private String description;

    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;

    @Min(0)
    private Integer stockQuantity;

    @NotNull(message = "Category is required")
    private UUID categoryId;
}