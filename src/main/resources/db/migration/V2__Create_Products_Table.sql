-- Create products table
CREATE TABLE products (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid (),
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    stock_quantity INTEGER NOT NULL DEFAULT 0,
    category_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW (),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW (),
    CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE CASCADE
);

-- Create index on name for search performance
CREATE INDEX idx_product_name ON products (name);

-- Create index on category_id for joins
CREATE INDEX idx_product_category ON products (category_id);