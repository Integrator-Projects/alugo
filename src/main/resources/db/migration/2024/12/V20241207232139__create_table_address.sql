CREATE TABLE addresses (
    id SERIAL PRIMARY KEY,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100) NOT NULL,
    zip_code VARCHAR(20),
    street VARCHAR(255) NOT NULL,
    neighborhood VARCHAR(100) NOT NULL,
    number INTEGER NOT NULL
);