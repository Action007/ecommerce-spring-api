package com.ecommerce.api.service;

import com.ecommerce.api.entity.Order;

public interface EmailService {
    void sendOrderConfirmation(Order order);
}
