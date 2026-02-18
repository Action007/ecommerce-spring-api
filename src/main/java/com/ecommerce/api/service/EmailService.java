package com.ecommerce.api.service;

import com.ecommerce.api.entity.Order;
import com.ecommerce.api.entity.User;

public interface EmailService {
    void sendOrderConfirmation(Order order);

    void sendPasswordResetEmail(User user, String resetToken);
}
