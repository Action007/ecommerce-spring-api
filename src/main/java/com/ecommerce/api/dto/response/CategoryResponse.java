package com.ecommerce.api.dto.response;

import java.time.Instant;
import java.util.UUID;

import lombok.Data;

@Data
public class CategoryResponse {
    private UUID id;
    private String name;
    private String description;
    private UUID parentId;
    private String parentName;
    private Instant createdAt;
}
