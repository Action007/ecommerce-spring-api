-- Insert 5 products
INSERT INTO
    products (
        id,
        name,
        description,
        price,
        stock_quantity,
        category_id,
        created_at,
        updated_at
    )
VALUES
    (
        '650e8400-e29b-41d4-a716-446655440001',
        'Laptop',
        'Gaming laptop with RTX 4060',
        1299.99,
        10,
        'c1eebc99-9c0b-4ef8-bb6d-6bb9bd380a33',
        NOW (),
        NOW ()
    ),
    (
        '650e8400-e29b-41d4-a716-446655440002',
        'Mouse',
        'Wireless gaming mouse',
        59.99,
        50,
        'c1eebc99-9c0b-4ef8-bb6d-6bb9bd380a33',
        NOW (),
        NOW ()
    ),
    (
        '650e8400-e29b-41d4-a716-446655440003',
        'Keyboard',
        'Mechanical RGB keyboard',
        129.99,
        30,
        'c1eebc99-9c0b-4ef8-bb6d-6bb9bd380a33',
        NOW (),
        NOW ()
    ),
    (
        '650e8400-e29b-41d4-a716-446655440004',
        'Monitor',
        '27-inch 144Hz gaming monitor',
        349.99,
        15,
        'c1eebc99-9c0b-4ef8-bb6d-6bb9bd380a33',
        NOW (),
        NOW ()
    ),
    (
        '650e8400-e29b-41d4-a716-446655440005',
        'Headset',
        'Noise-cancelling gaming headset',
        89.99,
        25,
        'c1eebc99-9c0b-4ef8-bb6d-6bb9bd380a33',
        NOW (),
        NOW ()
    );