CREATE DATABASE IF NOT EXISTS payments_db;
USE payments_db;

CREATE TABLE IF NOT EXISTS payments
(
    payment_id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id         BIGINT                                                       NOT NULL,
    user_id          BIGINT                                                       NOT NULL,
    is_payed         BOOLEAN                                                      NOT NULL,
    payment_status   ENUM ('NOT_STARTED', 'IN_PROGRESS', 'REFUNDED', 'COMPLETED') NOT NULL,
    communication_sw BOOLEAN                                                      NOT NULL,
    createdAt        DATETIME,
    createdBy        VARCHAR(255),
    updatedAt        DATETIME,
    updatedBy        VARCHAR(255)
);