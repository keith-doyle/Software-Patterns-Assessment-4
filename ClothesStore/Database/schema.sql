-- Create database
CREATE DATABASE IF NOT EXISTS clothes_store;
USE clothes_store;

-- Categories
CREATE TABLE categories (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

-- Manufacturers
CREATE TABLE manufacturers (
    manufacturer_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

-- Users
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    address TEXT,
    payment_method VARCHAR(100),
    loyalty_points INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Products
CREATE TABLE products (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    stock_quantity INT NOT NULL DEFAULT 0,
    image_path VARCHAR(255),
    category_id INT,
    manufacturer_id INT,
    average_rating DECIMAL(3,2) DEFAULT 0.00,
    active BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (category_id) REFERENCES categories(category_id),
    FOREIGN KEY (manufacturer_id) REFERENCES manufacturers(manufacturer_id)
);

-- Orders
CREATE TABLE orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    total_price DECIMAL(10,2),
    status VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Order Items
CREATE TABLE order_items (
    order_item_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    product_id INT,
    quantity INT,
    price DECIMAL(10,2),
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

-- Reviews
CREATE TABLE reviews (
    review_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    product_id INT,
    rating INT,
    comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

-- Seed Data
INSERT INTO categories (name) VALUES
('T-Shirts'), ('Hoodies'), ('Jeans'), ('Jackets');

INSERT INTO manufacturers (name) VALUES
('Nike'), ('Adidas'), ('Levis'), ('Puma');

INSERT INTO products (title, description, price, stock_quantity, image_path, category_id, manufacturer_id, average_rating, active) VALUES
('Nike Essential Tee', 'Soft cotton t-shirt', 24.99, 20, 'images/nike-tee.jpg', 1, 1, 4.5, TRUE),
('Adidas Zip Hoodie', 'Comfortable hoodie', 49.99, 15, 'images/adidas-hoodie.jpg', 2, 2, 4.2, TRUE),
('Levis 501 Jeans', 'Classic jeans', 69.99, 10, 'images/levis-jeans.jpg', 3, 3, 4.7, TRUE),
('Puma Track Jacket', 'Lightweight jacket', 59.99, 12, 'images/puma-jacket.jpg', 4, 4, 4.1, TRUE);

-- Admin user
INSERT INTO users (full_name, email, password, role)
VALUES ('Admin User', 'admin@clothesstore.com', 'admin123', 'ADMIN');