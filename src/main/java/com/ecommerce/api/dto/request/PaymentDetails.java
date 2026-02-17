package com.ecommerce.api.dto.request;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class PaymentDetails {
    private String cardNumber;
}