package com.ecommerce.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseEntity {

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    // Parent relationship (many categories → one parent)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id") // Creates parent_id column in DB
    private Category parent;

    // Children relationship (one category → many children)
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // CRITICAL: Prevents infinite JSON loop
    private Set<Category> children = new HashSet<>();
}