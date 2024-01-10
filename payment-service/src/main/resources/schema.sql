CREATE DATABASE IF NOT EXISTS payments_db;
USE payments_db;

CREATE TABLE IF NOT EXISTS payments
(
    payment_id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id         BIGINT,
    user_id          BIGINT,
    is_payed         BOOLEAN,
    payment_status   VARCHAR(50),
    communication_sw BOOLEAN,
    created_at       DATETIME,
    created_by       VARCHAR(255),
    updated_at       DATETIME,
    updated_by       VARCHAR(255)
);