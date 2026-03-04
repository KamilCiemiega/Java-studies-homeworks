-- 1. Create Categories Table
CREATE TABLE IF NOT EXISTS categories (
                                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                          name VARCHAR(50) NOT NULL UNIQUE
    );

-- 2. Create Users Table
CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL -- 'ADMIN' or 'USER'
    );

-- 3. Create Books Table
CREATE TABLE IF NOT EXISTS books (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    year_published INT,
    isbn VARCHAR(20),
    status VARCHAR(20) DEFAULT 'AVAILABLE', -- 'AVAILABLE' or 'RENTED'
    category_id BIGINT,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL
    );

-- 4. Create Rentals Table (New Feature!)
CREATE TABLE IF NOT EXISTS rentals (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       book_id BIGINT NOT NULL,
                                       user_id BIGINT NOT NULL,
                                       rental_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                       return_date TIMESTAMP NULL,
                                       FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
    );

-- ==========================================================
-- INITIAL DATA (Runs only on first DB creation)
-- ==========================================================

-- For Categories
INSERT INTO categories (name)
SELECT 'Fantasy' WHERE NOT EXISTS (SELECT 1 FROM categories WHERE name = 'Fantasy');
INSERT INTO categories (name)
SELECT 'Science Fiction' WHERE NOT EXISTS (SELECT 1 FROM categories WHERE name = 'Science Fiction');
INSERT INTO categories (name)
SELECT 'History' WHERE NOT EXISTS (SELECT 1 FROM categories WHERE name = 'History');
INSERT INTO categories (name)
SELECT 'Biography' WHERE NOT EXISTS (SELECT 1 FROM categories WHERE name = 'Biography');

-- For Users
INSERT INTO users (username, password_hash, role)
SELECT 'admin', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', 'ADMIN'
    WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'admin');

INSERT INTO users (username, password_hash, role)
SELECT 'kamil', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', 'USER'
    WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'kamil');

-- Add to the end of schema.sql
INSERT INTO books (title, author, year_published, isbn, status, category_id)
SELECT 'The Witcher', 'Andrzej Sapkowski', 1993, '12345', 'AVAILABLE', 1
    WHERE NOT EXISTS (SELECT 1 FROM books WHERE title = 'The Witcher');

INSERT INTO books (title, author, year_published, isbn, status, category_id)
SELECT 'Dune', 'Frank Herbert', 1965, '67890', 'AVAILABLE', 2
    WHERE NOT EXISTS (SELECT 1 FROM books WHERE title = 'Dune');