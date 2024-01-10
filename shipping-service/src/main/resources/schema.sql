CREATE DATABASE IF NOT EXISTS shippings_db;
USE shippings_db;

CREATE TABLE IF NOT EXISTS shipment_details
(
    shipment_details_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    recipient_name      VARCHAR(255),
    recipient_address   TEXT,
    recipient_phone     VARCHAR(50),
    shipping_cost       DOUBLE,
    weight              BIGINT,
    created_at          DATETIME,
    created_by          VARCHAR(255),
    updated_at          DATETIME,
    updated_by          VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS shipment
(
    shipment_id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id                BIGINT,
    tracking_number         VARCHAR(255),
    shipment_status         VARCHAR(50),
    tracking_info           TEXT,
    shipped_date            DATETIME,
    estimated_delivery_date DATETIME,
    shipment_details_id     BIGINT,
    created_at              DATETIME,
    created_by              VARCHAR(255),
    updated_at              DATETIME,
    updated_by              VARCHAR(255),
    FOREIGN KEY (shipment_details_id) REFERENCES shipment_details (shipment_details_id)
);

CREATE TABLE IF NOT EXISTS shipping_option
(
    shipping_option_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    carrier            VARCHAR(255),
    service_type       VARCHAR(255),
    cost               DOUBLE,
    delivery_speed     VARCHAR(50),
    description        TEXT,
    created_at         DATETIME,
    created_by         VARCHAR(255),
    updated_at         DATETIME,
    updated_by         VARCHAR(255)
);