package com.ecommerce.api.service;

import java.util.List;
import java.util.UUID;

import com.ecommerce.api.dto.request.UserUpdateRequest;
import com.ecommerce.api.dto.response.UserResponse;

public interface UserService {
  UserResponse getUserById(UUID id);

  List<UserResponse> getAllUsers();

  UserResponse updateUser(UUID id, UserUpdateRequest request);

  void deleteUser(UUID id);

  UserResponse getUserByEmail(String email);

  UserResponse getCurrentUser();

  UserResponse updateCurrentUser(UserUpdateRequest request);
}
