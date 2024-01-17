CREATE DATABASE IF NOT EXISTS orders_db;
USE orders_db;

CREATE TABLE IF NOT EXISTS carts
(
    cart_id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id   BIGINT NOT NULL,
    createdAt DATETIME,
    createdBy VARCHAR(255),
    updatedAt DATETIME,
    updatedBy VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS cart_items
(
    cart_item_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id   BIGINT NOT NULL,
    quantity     BIGINT NOT NULL CHECK (quantity >= 1),
    cart_id      BIGINT,
    createdAt    DATETIME,
    createdBy    VARCHAR(255),
    updatedAt    DATETIME,
    updatedBy    VARCHAR(255),
    FOREIGN KEY (cart_id) REFERENCES carts (cart_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS orders
(
    order_id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_date        DATETIME,
    order_description VARCHAR(255),
    total_amount      DECIMAL                                                 NOT NULL CHECK (total_amount >= 0),
    order_status      ENUM ('PREPARING', 'SHIPPED', 'DELIVERED', 'CANCELLED') NOT NULL,
    cart_id           BIGINT,
    createdAt         DATETIME,
    createdBy         VARCHAR(255),
    updatedAt         DATETIME,
    updatedBy         VARCHAR(255),
    FOREIGN KEY (cart_id) REFERENCES carts (cart_id) ON DELETE CASCADE
);