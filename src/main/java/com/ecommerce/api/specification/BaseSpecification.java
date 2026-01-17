package com.ecommerce.api.specification;

import org.springframework.data.jpa.domain.Specification;
import com.ecommerce.api.entity.BaseEntity;

public class BaseSpecification {

    public static <T extends BaseEntity> Specification<T> notDeleted() {
        return (root, query, cb) -> cb.equal(root.get("deleted"), false);
    }
}