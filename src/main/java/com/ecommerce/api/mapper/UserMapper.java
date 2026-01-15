package com.ecommerce.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.ecommerce.api.dto.request.UserRequest;
import com.ecommerce.api.dto.response.UserResponse;
import com.ecommerce.api.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
  UserResponse toResponse(User user);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "role", ignore = true)
  @Mapping(target = "deleted", ignore = true)
  @Mapping(target = "deletedAt", ignore = true)
  User toEntity(UserRequest request);
}
