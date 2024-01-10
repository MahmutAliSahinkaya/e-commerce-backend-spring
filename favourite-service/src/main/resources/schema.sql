CREATE DATABASE IF NOT EXISTS favourites_db;
USE favourites_db;

CREATE TABLE IF NOT EXISTS favourites
(
    user_id    BIGINT   NOT NULL,
    product_id BIGINT   NOT NULL,
    like_date  DATETIME NOT NULL,
    created_at DATETIME,
    created_by VARCHAR(255),
    updated_at DATETIME,
    updated_by VARCHAR(255),
    PRIMARY KEY (user_id, product_id, like_date)
);