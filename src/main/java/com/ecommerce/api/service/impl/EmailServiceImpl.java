package com.ecommerce.api.service.impl;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ecommerce.api.entity.Order;
import com.ecommerce.api.entity.User;
import com.ecommerce.api.service.EmailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    @Async
    public void sendOrderConfirmation(Order order) {
        // Simulate email sending
        log.info("Sending order confirmation to: " + order.getUser().getEmail());

        try {
            Thread.sleep(2000); // Simulate SMTP delay

            String emailBody = String.format("""
                    Order Confirmation
                    Order Number: %s
                    Total Amount: $%s
                    Status: %s
                    Thank you for your order!
                    """, order.getOrderNumber(), order.getTotalAmount(), order.getStatus());

            // In production: use JavaMailSender
            log.info("Email body:\n{}", emailBody);
            log.info("Email sent successfully for order: {}", order.getOrderNumber());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Email sending interrupted", e);
        }
    }
}
