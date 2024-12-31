CREATE TABLE buildings (
    id SERIAL PRIMARY KEY,
    number_of_floors INTEGER,
    description TEXT,
    address_id BIGINT,
    CONSTRAINT fk_address FOREIGN KEY (address_id) REFERENCES addresses (id) ON DELETE CASCADE
);
