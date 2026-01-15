package com.ecommerce.api.service;

import java.util.UUID;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ecommerce.api.dto.request.UserRequest;
import com.ecommerce.api.dto.response.UserResponse;
import com.ecommerce.api.entity.Role;
import com.ecommerce.api.entity.User;
import com.ecommerce.api.repository.UserRepository;
import com.ecommerce.api.service.impl.UserServiceImpl;
import com.ecommerce.api.util.UserMapper;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;
    @Mock
    private UserMapper mapper;
    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserRequest userRequest;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        UUID userId = UUID.randomUUID();

        userRequest = new UserRequest();
        userRequest.setFirstName("John");
        userRequest.setLastName("Doe");
        userRequest.setEmail("john@test.com");
        userRequest.setPassword("password123");

        user = new User();
        user.setId(userId);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john@test.com");
        user.setPassword("password123");
        user.setRole(Role.CUSTOMER);

        userResponse = new UserResponse();
        userResponse.setId(userId);
        userResponse.setFirstName("John");
        userResponse.setLastName("Doe");
        userResponse.setEmail("john@test.com");
        userResponse.setRole(Role.CUSTOMER);
    }

    @Test
    void createUser_WithValidData_ReturnsUserResponse() {

    }

    // Test: createUser with valid data
    // Test: createUser with duplicate email
    // Test: getUserById when exists
    // Test: getUserById when not found
}