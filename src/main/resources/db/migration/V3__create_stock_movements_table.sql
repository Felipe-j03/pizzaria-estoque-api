CREATE TABLE stock_movements
(
    id            UUID PRIMARY KEY,
    product_id    UUID NOT NULL REFERENCES products(id),
    user_id       UUID NOT NULL REFERENCES users(id),
    type          VARCHAR(10) NOT NULL,
    quantity      REAL NOT NULL,
    note          VARCHAR(500),
    created_at    TIMESTAMP    NOT NULL
);