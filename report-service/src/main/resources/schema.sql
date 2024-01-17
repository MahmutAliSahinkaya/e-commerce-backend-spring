CREATE DATABASE IF NOT EXISTS reports_db;
USE reports_db;

CREATE TABLE IF NOT EXISTS report_item
(
    reportItemId BIGINT AUTO_INCREMENT PRIMARY KEY,
    identifier VARCHAR(255) NOT NULL,
    grossSales FLOAT NOT NULL CHECK (grossSales >= 0),
    netSales FLOAT NOT NULL CHECK (netSales >= 0),
    ordersCount INT NOT NULL CHECK (ordersCount >= 0),
    productsCount INT NOT NULL CHECK (productsCount >= 0),
    createdAt DATETIME,
    createdBy VARCHAR(255),
    updatedAt DATETIME,
    updatedBy VARCHAR(255)
);