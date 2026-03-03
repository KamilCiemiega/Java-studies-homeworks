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

-- Add default categories
INSERT IGNORE INTO categories (name) VALUES ('Fantasy'), ('Science Fiction'), ('History'), ('Biography');

-- Add an Admin user (Username: admin | Password: admin123)
-- The hash below is generated using SHA-256
INSERT IGNORE INTO users (username, password_hash, role)
VALUES ('admin', '240be518ebb2146c006fd2c091c05d9daa46a2d120c484f0775d742886395e86', 'ADMIN');

-- Add a test user (Username: kamil | Password: user123)
INSERT IGNORE INTO users (username, password_hash, role)
VALUES ('kamil', '0b4e7a0e5f1a7ad05f420e6988f01b7a2d64f0f6702f3598d92f58b09312e08c', 'USER');

-- Add some starter books
INSERT IGNORE INTO books (title, author, year_published, status, category_id)
VALUES ('The Witcher', 'Andrzej Sapkowski', 1993, 'AVAILABLE', 1);

INSERT IGNORE INTO books (title, author, year_published, status, category_id)
VALUES ('Dune', 'Frank Herbert', 1965, 'AVAILABLE', 2);