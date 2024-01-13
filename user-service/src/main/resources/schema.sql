CREATE DATABASE IF NOT EXISTS users_db;
USE users_db;

CREATE TABLE IF NOT EXISTS Users
(
    user_id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    username   VARCHAR(255) NOT NULL UNIQUE,
    first_name VARCHAR(255),
    last_name  VARCHAR(255),
    email      VARCHAR(255) NOT NULL UNIQUE,
    image_url  VARCHAR(255),
    phone      VARCHAR(255),
    created_at DATETIME,
    created_by VARCHAR(255),
    updated_at DATETIME,
    updated_by VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS address
(
    address_id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    full_address VARCHAR(255) UNIQUE,
    postal_code  VARCHAR(255),
    city         VARCHAR(255),
    user_id      BIGINT,
    created_at   DATETIME,
    created_by   VARCHAR(255),
    updated_at   DATETIME,
    updated_by   VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES Users (user_id)
);