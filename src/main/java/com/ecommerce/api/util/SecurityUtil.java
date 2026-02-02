package com.ecommerce.api.util;

import com.ecommerce.api.entity.User;
import com.ecommerce.api.exception.UnauthorizedException;
import com.ecommerce.api.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public class SecurityUtil {

    public static User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            throw new UnauthorizedException("Not authenticated");
        }

        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        return userDetails.getUser();
    }

    public static UUID getCurrentUserId() {
        return getCurrentUser().getId();
    }

    public static String getCurrentUserEmail() {
        return getCurrentUser().getEmail();
    }

    public static boolean isAdmin() {
        return getCurrentUser().getRole().name().equals("ADMIN");
    }
}