CREATE TABLE houses (
    id SERIAL PRIMARY KEY,
    rental_price NUMERIC(10, 2) NOT NULL, -- Preço do aluguel com até duas casas decimais
    description TEXT NOT NULL,
    available BOOLEAN NOT NULL,
    number_of_bathrooms INTEGER NOT NULL,
    number_of_bedrooms INTEGER NOT NULL,
    area_in_m2 NUMERIC(10, 2) NOT NULL,
    has_garage BOOLEAN NOT NULL,
    address_id BIGINT NOT NULL,
    CONSTRAINT fk_address FOREIGN KEY (address_id) REFERENCES addresses (id) ON DELETE CASCADE
);
