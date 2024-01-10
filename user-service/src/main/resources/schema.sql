CREATE DATABASE IF NOT EXISTS users_db;
USE users_db;

CREATE TABLE IF NOT EXISTS Users
(
    user_id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    username   VARCHAR(255) NOT NULL UNIQUE,
    first_name VARCHAR(255),
    last_name  VARCHAR(255),
    email      VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255),
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

CREATE TABLE IF NOT EXISTS refresh_token
(
    refresh_token_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id          BIGINT,
    token            VARCHAR(255) NOT NULL UNIQUE,
    expiry_date      TIMESTAMP    NOT NULL,
    created_at       DATETIME,
    created_by       VARCHAR(255),
    updated_at       DATETIME,
    updated_by       VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES Users (user_id)
);

CREATE TABLE IF NOT EXISTS Roles
(
    role_id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(20) UNIQUE,
    created_at DATETIME,
    created_by VARCHAR(255),
    updated_at DATETIME,
    updated_by VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS user_roles
(
    user_id BIGINT,
    role_id BIGINT,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES Users (user_id),
    FOREIGN KEY (role_id) REFERENCES Roles (role_id)
);