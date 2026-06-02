CREATE TABLE products
(
    id               UUID PRIMARY KEY,
    name             VARCHAR(255) NOT NULL UNIQUE,
    unit             VARCHAR(255) NOT NULL,
    current_quantity REAL NOT NULL,
    min_quantity     REAL NOT NULL,
    active           BOOLEAN      NOT NULL,
    created_at       TIMESTAMP    NOT NULL
);