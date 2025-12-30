package com.ecommerce.api.service;

import java.util.List;
import java.util.UUID;
import com.ecommerce.api.dto.request.UserRequest;
import com.ecommerce.api.dto.response.UserResponse;

public interface UserService {
  UserResponse createUser(UserRequest request);

  UserResponse getUserById(UUID id);

  List<UserResponse> getAllUsers();

  UserResponse updateUser(UUID id, UserRequest request);

  void deleteUser(UUID id);

  UserResponse getUserByEmail(String email);
}
