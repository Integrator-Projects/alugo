CREATE TABLE apartments (
    id SERIAL PRIMARY KEY,
    number INTEGER NOT NULL,
    floor INTEGER NOT NULL,
    available BOOLEAN NOT NULL,
    number_of_rooms INTEGER NOT NULL,
    number_of_bathrooms INTEGER NOT NULL,
    area_in_m2 DOUBLE PRECISION NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    description TEXT,
    building_id BIGINT NOT NULL,
    CONSTRAINT fk_building FOREIGN KEY (building_id) REFERENCES buildings (id) ON DELETE CASCADE
);
