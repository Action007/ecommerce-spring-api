package com.ecommerce.api.service.impl;

import java.math.BigDecimal;
import java.time.Instant;

import org.springframework.stereotype.Service;

import com.ecommerce.api.dto.request.PaymentDetails;
import com.ecommerce.api.entity.Order;
import com.ecommerce.api.entity.OrderStatus;
import com.ecommerce.api.exception.PaymentFailedException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl {
    @Service
    public class PaymentService {
        public void processPayment(Order order, PaymentDetails details) {
            // Simulate payment processing
            if (details.getCardNumber().startsWith("4000")) {
                // Test card for failure
                throw new PaymentFailedException("Payment declined");
            }

            if (order.getTotalAmount().compareTo(new BigDecimal("10000")) > 0) {
                throw new PaymentFailedException("Amount too high for test");
            }

            // Simulate network delay
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Success: update order
            order.setPaidAt(Instant.now());
            order.setStatus(OrderStatus.PROCESSING);
        }
    }
}
