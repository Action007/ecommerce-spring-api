-- Add image_url column to products table
ALTER TABLE products
ADD COLUMN image_url VARCHAR(500);

-- Set default image for all existing products
UPDATE products
SET
    image_url = 'https://media.tenor.com/Vsa5nvYxHrYAAAAM/no.gif'
WHERE
    image_url IS NULL;

-- Add rating columns
ALTER TABLE products
ADD COLUMN average_rating DECIMAL(3, 2) DEFAULT 0.00,
ADD COLUMN rating_count INTEGER DEFAULT 0;

-- Set defaults for existing products
UPDATE products
SET
    average_rating = 0.00,
    rating_count = 0
WHERE
    average_rating IS NULL
    OR rating_count IS NULL;