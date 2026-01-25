package com.ecommerce.api.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ecommerce.api.entity.User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CustomUserDetails implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Add logic later if needed
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Add logic later if needed (e.g., after failed login attempts)
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Add logic later if needed (e.g., password expiry)
    }

    @Override
    public boolean isEnabled() {
        return !user.getDeleted();
    }
}
