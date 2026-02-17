-- File: src/main/resources/db/migration/V3__Add_Cart_And_Order_Tables.sql
-- (use whatever version number comes next after your existing migrations)
-- Cart table
CREATE TABLE carts (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    deleted BOOLEAN NOT NULL DEFAULT false,
    deleted_at TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

-- Cart items table
CREATE TABLE cart_items (
    id UUID PRIMARY KEY,
    cart_id UUID NOT NULL,
    product_id UUID NOT NULL,
    quantity INTEGER NOT NULL,
    price_at_add DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    deleted BOOLEAN NOT NULL DEFAULT false,
    deleted_at TIMESTAMP,
    FOREIGN KEY (cart_id) REFERENCES carts (id),
    FOREIGN KEY (product_id) REFERENCES products (id),
    UNIQUE (cart_id, product_id)
);

-- Orders table
CREATE TABLE orders (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    order_number VARCHAR(255) NOT NULL UNIQUE,
    status VARCHAR(50) NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    shipping_address TEXT,
    payment_method VARCHAR(255),
    paid_at TIMESTAMP,
    shipped_at TIMESTAMP,
    delivered_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    deleted BOOLEAN NOT NULL DEFAULT false,
    deleted_at TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

-- Order items table
CREATE TABLE order_items (
    id UUID PRIMARY KEY,
    order_id UUID NOT NULL,
    product_id UUID NOT NULL,
    quantity INTEGER NOT NULL,
    price_at_purchase DECIMAL(10, 2) NOT NULL,
    product_name_at_purchase VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    deleted BOOLEAN NOT NULL DEFAULT false,
    deleted_at TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders (id),
    FOREIGN KEY (product_id) REFERENCES products (id),
    UNIQUE (order_id, product_id)
);

CREATE INDEX idx_carts_user ON carts (user_id);

CREATE INDEX idx_cart_items_cart ON cart_items (cart_id);

CREATE INDEX idx_orders_user ON orders (user_id);

CREATE INDEX idx_order_items_order ON order_items (order_id);