-- Shopping Mall Database Schema

-- Create Products table
CREATE TABLE IF NOT EXISTS products (
    product_id INTEGER PRIMARY KEY AUTOINCREMENT,
    product_name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    stock_quantity INTEGER NOT NULL DEFAULT 0,
    category VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Customers table
CREATE TABLE IF NOT EXISTS customers (
    customer_id INTEGER PRIMARY KEY AUTOINCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    address TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Orders table
CREATE TABLE IF NOT EXISTS orders (
    order_id INTEGER PRIMARY KEY AUTOINCREMENT,
    customer_id INTEGER NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    order_status VARCHAR(20) DEFAULT 'PENDING',
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

-- Create Order Items table
CREATE TABLE IF NOT EXISTS order_items (
    order_item_id INTEGER PRIMARY KEY AUTOINCREMENT,
    order_id INTEGER NOT NULL,
    product_id INTEGER NOT NULL,
    quantity INTEGER NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

-- Create Cart table (for temporary shopping cart)
CREATE TABLE IF NOT EXISTS cart (
    cart_id INTEGER PRIMARY KEY AUTOINCREMENT,
    customer_id INTEGER NOT NULL,
    product_id INTEGER NOT NULL,
    quantity INTEGER NOT NULL,
    added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

-- Insert sample products
INSERT INTO products (product_name, description, price, stock_quantity, category) VALUES
    ('Laptop', 'High-performance laptop with 16GB RAM', 899.99, 50, 'Electronics'),
    ('Smartphone', 'Latest model smartphone with 5G', 699.99, 100, 'Electronics'),
    ('T-Shirt', 'Cotton T-Shirt - Multiple colors', 19.99, 200, 'Clothing'),
    ('Jeans', 'Denim Jeans - Various sizes', 49.99, 150, 'Clothing'),
    ('Running Shoes', 'Comfortable running shoes', 79.99, 80, 'Footwear'),
    ('Coffee Maker', 'Automatic coffee maker', 129.99, 30, 'Home Appliances'),
    ('Backpack', 'Durable travel backpack', 39.99, 120, 'Accessories'),
    ('Headphones', 'Wireless noise-canceling headphones', 199.99, 60, 'Electronics'),
    ('Watch', 'Stylish analog watch', 149.99, 40, 'Accessories'),
    ('Microwave', 'Compact microwave oven', 89.99, 25, 'Home Appliances');
