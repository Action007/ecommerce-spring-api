package com.ecommerce.api.dto.response;

import java.time.Instant;
import java.util.UUID;
import com.ecommerce.api.entity.Role;
import lombok.Data;

@Data
public class UserResponse {
  private UUID id;
  private String firstName;
  private String lastName;
  private String email;
  private Role role;
  private Instant createdAt;
}
