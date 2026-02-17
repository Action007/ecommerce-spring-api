package com.ecommerce.api.service;

import com.ecommerce.api.dto.request.PaymentDetails;
import com.ecommerce.api.entity.Order;

public interface PaymentService {
    void processPayment(Order order, PaymentDetails details);
}
