CREATE DATABASE IF NOT EXISTS products_db;
USE products_db;

CREATE TABLE IF NOT EXISTS categories
(
    category_id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    category_title     VARCHAR(255) NOT NULL,
    image_url          VARCHAR(255),
    parent_category_id BIGINT,
    createdAt          DATETIME,
    createdBy          VARCHAR(255),
    updatedAt          DATETIME,
    updatedBy          VARCHAR(255),
    FOREIGN KEY (parent_category_id) REFERENCES categories (category_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS products
(
    product_id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_title VARCHAR(255) NOT NULL,
    image_url     VARCHAR(255),
    sku           VARCHAR(255) NOT NULL UNIQUE,
    price_unit    DECIMAL      NOT NULL CHECK (price_unit >= 0),
    quantity      INT          NOT NULL CHECK (quantity >= 0),
    category_id   BIGINT,
    createdAt     DATETIME,
    createdBy     VARCHAR(255),
    updatedAt     DATETIME,
    updatedBy     VARCHAR(255),
    FOREIGN KEY (category_id) REFERENCES categories (category_id) ON DELETE CASCADE
);