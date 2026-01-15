-- Create users table
CREATE TABLE users (
    id UUID PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

-- Create categories table
CREATE TABLE categories (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    parent_id UUID,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_category_parent FOREIGN KEY (parent_id) REFERENCES categories (id)
);

-- Seed data
INSERT INTO
    users (
        id,
        first_name,
        last_name,
        email,
        password,
        role,
        deleted,
        created_at,
        updated_at
    )
VALUES
    (
        'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11',
        'Admin',
        'User',
        'admin@example.com',
        'admin123',
        'ADMIN',
        FALSE,
        NOW(),
        NOW()
    ),
    (
        'b1eebc99-9c0b-4ef8-bb6d-6bb9bd380a22',
        'John',
        'Doe',
        'customer@example.com',
        'customer123',
        'CUSTOMER',
        FALSE,
        NOW(),
        NOW()
    );

INSERT INTO
    categories (
        id,
        name,
        description,
        parent_id,
        created_at,
        updated_at
    )
VALUES
    (
        'c1eebc99-9c0b-4ef8-bb6d-6bb9bd380a33',
        'Electronics',
        'Electronic devices',
        NULL,
        NOW(),
        NOW()
    ),
    (
        'c2eebc99-9c0b-4ef8-bb6d-6bb9bd380a44',
        'Clothing',
        'Apparel and fashion',
        NULL,
        NOW(),
        NOW()
    ),
    (
        'd1eebc99-9c0b-4ef8-bb6d-6bb9bd380a55',
        'Laptops',
        'Portable computers',
        'c1eebc99-9c0b-4ef8-bb6d-6bb9bd380a33',
        NOW(),
        NOW()
    ),
    (
        'd2eebc99-9c0b-4ef8-bb6d-6bb9bd380a66',
        'Phones',
        'Mobile phones',
        'c1eebc99-9c0b-4ef8-bb6d-6bb9bd380a33',
        NOW(),
        NOW()
    );