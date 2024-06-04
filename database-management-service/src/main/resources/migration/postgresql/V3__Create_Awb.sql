CREATE TABLE awbs (
    id SERIAL PRIMARY KEY,
    unique_number VARCHAR(100) NOT NULL,
    shipment_address VARCHAR(1000) NOT NULL,
    delivery_address VARCHAR(1000) NOT NULL,
    ordered_by_id INTEGER NOT NULL,
    created_at DATE NOT NULL,
    last_updated_at DATE NOT NULL,
    status VARCHAR(50) NOT NULL,
    price REAL NOT NULL,
    CONSTRAINT fk_awbs_users FOREIGN KEY (ordered_by_id) REFERENCES users(id)
);

ALTER TABLE awb_delivery_route_relation ADD CONSTRAINT fk_relation_awbs FOREIGN KEY (awb_id) REFERENCES awbs(id);
