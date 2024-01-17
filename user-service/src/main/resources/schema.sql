CREATE DATABASE IF NOT EXISTS users_db;
USE users_db;

CREATE TABLE IF NOT EXISTS Users
(
    userId     BIGINT AUTO_INCREMENT,
    username   VARCHAR(50)  NOT NULL UNIQUE,
    first_name VARCHAR(50)  NOT NULL,
    last_name  VARCHAR(50)  NOT NULL,
    email      VARCHAR(255) NOT NULL UNIQUE,
    image_url  VARCHAR(255),
    phone      VARCHAR(15)  NOT NULL,
    createdAt  DATETIME,
    createdBy  VARCHAR(255),
    updatedAt  DATETIME,
    updatedBy  VARCHAR(255),
    PRIMARY KEY (userId)
);

CREATE TABLE IF NOT EXISTS address
(
    address_id   BIGINT AUTO_INCREMENT,
    full_address VARCHAR(200) NOT NULL UNIQUE,
    postal_code  VARCHAR(50)  NOT NULL,
    city         VARCHAR(50)  NOT NULL,
    user_id      BIGINT,
    createdAt    DATETIME,
    createdBy    VARCHAR(255),
    updatedAt    DATETIME,
    updatedBy    VARCHAR(255),
    PRIMARY KEY (address_id),
    FOREIGN KEY (user_id) REFERENCES Users (userId) ON DELETE CASCADE
);