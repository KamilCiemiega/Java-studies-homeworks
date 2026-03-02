CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL -- USER or ADMIN
    );

CREATE TABLE IF NOT EXISTS books (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    year_published INT,
    isbn VARCHAR(20),
    status VARCHAR(20) DEFAULT 'AVAILABLE'
    );

CREATE TABLE IF NOT EXISTS rentals (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       book_id BIGINT NOT NULL,
                                       user_id BIGINT NOT NULL,
                                       rental_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                       return_date TIMESTAMP,
                                       FOREIGN KEY (book_id) REFERENCES books(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
    );

CREATE TABLE IF NOT EXISTS categories (
                                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                          name VARCHAR(50) NOT NULL UNIQUE
    );

ALTER TABLE books ADD COLUMN category_id BIGINT;
ALTER TABLE books ADD FOREIGN KEY (category_id) REFERENCES categories(id);