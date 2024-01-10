CREATE DATABASE IF NOT EXISTS reports_db;
USE reports_db;

CREATE TABLE IF NOT EXISTS report_item
(
    report_item_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    identifier     VARCHAR(255),
    gross_sales    FLOAT,
    net_sales      FLOAT,
    orders_count   INT,
    products_count INT,
    created_at     DATETIME,
    created_by     VARCHAR(255),
    updated_at     DATETIME,
    updated_by     VARCHAR(255)
);