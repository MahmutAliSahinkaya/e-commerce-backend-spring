CREATE DATABASE IF NOT EXISTS shippings_db;
USE shippings_db;

CREATE TABLE IF NOT EXISTS shipment_details
(
    shipmentDetailsId BIGINT AUTO_INCREMENT PRIMARY KEY,
    recipientName     VARCHAR(255) NOT NULL,
    recipientAddress  VARCHAR(255) NOT NULL,
    recipientPhone    VARCHAR(255) NOT NULL,
    shippingCost      DECIMAL      NOT NULL,
    weight            BIGINT       NOT NULL,
    createdAt         DATETIME,
    createdBy         VARCHAR(255),
    updatedAt         DATETIME,
    updatedBy         VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS shipment
(
    shipmentId            BIGINT AUTO_INCREMENT PRIMARY KEY,
    orderId               BIGINT                                                NOT NULL,
    trackingNumber        VARCHAR(255)                                          NOT NULL,
    shipmentStatus        ENUM ('PENDING', 'SHIPPED', 'CANCELLED', 'DELIVERED') NOT NULL,
    trackingInfo          VARCHAR(255),
    shippedDate           DATETIME,
    estimatedDeliveryDate DATETIME,
    shipment_details_id   BIGINT,
    createdAt             DATETIME,
    createdBy             VARCHAR(255),
    updatedAt             DATETIME,
    updatedBy             VARCHAR(255),
    FOREIGN KEY (shipment_details_id) REFERENCES shipment_details (shipmentDetailsId)
);

CREATE TABLE IF NOT EXISTS shipping_option
(
    shippingOptionId BIGINT AUTO_INCREMENT PRIMARY KEY,
    carrier          VARCHAR(255)            NOT NULL,
    serviceType      VARCHAR(255)            NOT NULL,
    cost             DECIMAL                 NOT NULL,
    deliverySpeed    ENUM ('NORMAL', 'FAST') NOT NULL,
    description      VARCHAR(255),
    createdAt        DATETIME,
    createdBy        VARCHAR(255),
    updatedAt        DATETIME,
    updatedBy        VARCHAR(255)
);