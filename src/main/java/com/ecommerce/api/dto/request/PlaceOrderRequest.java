package com.ecommerce.api.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class PlaceOrderRequest {
    @NotBlank
    private String shippingAddress;

    @NotBlank
    private String paymentMethod;

    @Valid
    @NotNull
    private PaymentDetails paymentDetails;
}