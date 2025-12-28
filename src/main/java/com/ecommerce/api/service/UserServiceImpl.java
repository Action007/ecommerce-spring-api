package com.ecommerce.api.service;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Use Spring's, not Jakarta
import com.ecommerce.api.dto.request.UserRequest;
import com.ecommerce.api.dto.response.UserResponse;
import com.ecommerce.api.entity.User;
import com.ecommerce.api.repository.UserRepository;
import com.ecommerce.api.util.UserMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Override
  @Transactional
  public UserResponse createUser(UserRequest request) {
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new RuntimeException("Email already exists");
    }

    User user = userMapper.toEntity(request);
    User savedUser = userRepository.save(user);

    return userMapper.toResponse(savedUser);
  }

  @Override
  public UserResponse getUserById(UUID id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

    return userMapper.toResponse(user);
  }

  @Override
  public List<UserResponse> getAllUsers() {
    List<User> users = userRepository.findAll();
    return users.stream().map(userMapper::toResponse).toList();
  }

  @Override
  @Transactional
  public UserResponse updateUser(UUID id, UserRequest request) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

    user.setFirstName(request.getFirstName());
    user.setLastName(request.getLastName());
    user.setEmail(request.getEmail());
    user.setPassword(request.getPassword());

    User updatedUser = userRepository.save(user);
    return userMapper.toResponse(updatedUser);
  }

  @Override
  @Transactional
  public void deleteUser(UUID id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

    userRepository.delete(user);
  }
}
