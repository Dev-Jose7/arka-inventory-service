CREATE DATABASE IF NOT EXISTS arka_inventory;
USE arka_inventory;

CREATE TABLE brand (
    id CHAR(36) PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE category (
    id CHAR(36) PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE country (
	id CHAR(36) PRIMARY KEY,
    name varchar(100) NOT NULL UNIQUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE warehouse (
    id CHAR(36) PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    location VARCHAR(200) NOT NULL,
    country_id CHAR(36) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (country_id) REFERENCES country(id)
);

CREATE TABLE product (
    id CHAR(36) PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    description TEXT,
    brand_id CHAR(36) NOT NULL,
    category_id CHAR(36) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    UNIQUE KEY uk_product_name_brand (name, brand_id),

    FOREIGN KEY (brand_id) REFERENCES brand(id),
    FOREIGN KEY (category_id) REFERENCES category(id)
);

CREATE TABLE currency (
	id CHAR(36) PRIMARY KEY,
    name varchar(100) NOT NULL UNIQUE,
    symbol varchar(5) NOT NULL,
    code varchar(10) NOT NULL UNIQUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE product_variant (
    id CHAR(36) PRIMARY KEY,
    sku VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    product_id CHAR(36) NOT NULL,
    currency_id CHAR(36) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    INDEX idx_product_id (product_id),

    UNIQUE KEY uk_variant_name_product (name, product_id),
    FOREIGN KEY (product_id) REFERENCES product(id),
    FOREIGN KEY (currency_id) REFERENCES currency(id)
);

CREATE TABLE stock (
    id CHAR(36) PRIMARY KEY,
    product_variant_id CHAR(36) NOT NULL,
    warehouse_id CHAR(36) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    UNIQUE KEY uk_stock_variant_warehouse (product_variant_id, warehouse_id),
    FOREIGN KEY (product_variant_id) REFERENCES product_variant(id),
    FOREIGN KEY (warehouse_id) REFERENCES warehouse(id)
);

CREATE TABLE stock_movement (
    id CHAR(36) PRIMARY KEY,
    stock_id CHAR(36) NOT NULL,
    type VARCHAR(20) NOT NULL,
    quantity INT NOT NULL,
    description TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (stock_id) REFERENCES stock(id)
);

CREATE TABLE stock_threshold (
    id CHAR(36) PRIMARY KEY,
    stock_id CHAR(36) NOT NULL UNIQUE,
    min_quantity INT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (stock_id) REFERENCES stock(id)
);