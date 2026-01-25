package com.ecommerce.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.api.dto.request.UserRequest;
import com.ecommerce.api.dto.response.UserResponse;
import com.ecommerce.api.entity.Role;
import com.ecommerce.api.entity.User;
import com.ecommerce.api.repository.UserRepository;
import com.ecommerce.api.security.CustomUserDetailsService;
import com.ecommerce.api.service.UserService;
import com.ecommerce.api.util.JwtUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil; // Add this
    private final UserRepository userRepository; // Add this

    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.createUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable UUID id) {
        UserResponse userResponse = userService.getUserById(id);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> userResponse = userService.getAllUsers();
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponse> getUserByEmail(@Valid @PathVariable String email) {
        UserResponse userResponse = userService.getUserByEmail(email);
        return ResponseEntity.ok(userResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable UUID id,
            @Valid @RequestBody UserRequest request) {
        UserResponse updatedUser = userService.updateUser(id, request);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // ===== TEMPORARY JWT TEST ENDPOINT - DELETE AFTER PHASE 3.7 =====
    @GetMapping("/test-jwt/{userId}")
    public ResponseEntity<String> testJwt(@PathVariable UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);

        // Validate token
        boolean isValid = jwtUtil.validateToken(accessToken);

        // Extract claims
        UUID extractedId = jwtUtil.extractUserId(accessToken);
        String extractedEmail = jwtUtil.extractEmail(accessToken);
        String extractedRole = jwtUtil.extractRole(accessToken);

        String response = String.format("""
                === JWT TEST RESULTS ===

                Access Token:
                %s

                Refresh Token:
                %s

                Validation: %s

                Extracted Claims:
                - User ID: %s
                - Email: %s
                - Role: %s

                🔍 Copy access token and paste at https://jwt.io to decode

                ⚠️  DELETE THIS ENDPOINT AFTER TESTING
                """,
                accessToken,
                refreshToken,
                isValid ? "✅ Valid" : "❌ Invalid",
                extractedId,
                extractedEmail,
                extractedRole);

        return ResponseEntity.ok(response);
    }

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @GetMapping("/test-user-details/{email}")
    public ResponseEntity<String> testUserDetails(@PathVariable String email) {
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            String response = String.format("""
                    === UserDetails Test ===

                    Username: %s
                    Authorities: %s
                    Enabled: %s
                    Account Non Expired: %s
                    Account Non Locked: %s
                    Credentials Non Expired: %s

                    ✅ UserDetailsService working!
                    """,
                    userDetails.getUsername(),
                    userDetails.getAuthorities(),
                    userDetails.isEnabled(),
                    userDetails.isAccountNonExpired(),
                    userDetails.isAccountNonLocked(),
                    userDetails.isCredentialsNonExpired());

            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(404).body("User not found: " + e.getMessage());
        }
    }
}