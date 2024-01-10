CREATE DATABASE IF NOT EXISTS products_db;
USE products_db;

CREATE TABLE IF NOT EXISTS categories
(
    category_id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    category_title     VARCHAR(255),
    image_url          VARCHAR(255),
    parent_category_id BIGINT,
    created_at         DATETIME,
    created_by         VARCHAR(255),
    updated_at         DATETIME,
    updated_by         VARCHAR(255),
    FOREIGN KEY (parent_category_id) REFERENCES categories (category_id)
);

CREATE TABLE IF NOT EXISTS products
(
    product_id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_title VARCHAR(255),
    image_url     VARCHAR(255),
    sku           VARCHAR(255) UNIQUE,
    price_unit    DECIMAL(10, 2),
    quantity      INT,
    category_id   BIGINT,
    created_at    DATETIME,
    created_by    VARCHAR(255),
    updated_at    DATETIME,
    updated_by    VARCHAR(255),
    FOREIGN KEY (category_id) REFERENCES categories (category_id)
);