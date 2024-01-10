CREATE DATABASE IF NOT EXISTS orders_db;
USE orders_db;

CREATE TABLE IF NOT EXISTS carts
(
    cart_id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id    BIGINT,
    created_at DATETIME,
    created_by VARCHAR(255),
    updated_at DATETIME,
    updated_by VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS cart_items
(
    cart_item_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id   BIGINT,
    quantity     BIGINT,
    cart_id      BIGINT,
    created_at   DATETIME,
    created_by   VARCHAR(255),
    updated_at   DATETIME,
    updated_by   VARCHAR(255),
    FOREIGN KEY (cart_id) REFERENCES carts (cart_id)
);

CREATE TABLE IF NOT EXISTS orders
(
    order_id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_date        DATETIME,
    order_description TEXT,
    total_amount      DECIMAL(10, 2),
    order_status      VARCHAR(50),
    cart_id           BIGINT,
    created_at        DATETIME,
    created_by        VARCHAR(255),
    updated_at        DATETIME,
    updated_by        VARCHAR(255),
    FOREIGN KEY (cart_id) REFERENCES carts (cart_id)
);