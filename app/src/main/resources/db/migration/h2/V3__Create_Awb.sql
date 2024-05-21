CREATE TABLE awbs (
    id IDENTITY PRIMARY KEY,
    unique_number VARCHAR(100) NOT NULL,
    shipment_address VARCHAR(1000) NOT NULL,
    delivery_address VARCHAR(1000) NOT NULL,
    ordered_by_id INTEGER NOT NULL,
    created_at DATE NOT NULL,
    last_updated_at DATE NOT NULL,
    status VARCHAR(50) NOT NULL,
    price REAL NOT NULL,
    FOREIGN KEY (ordered_by_id) REFERENCES users(id)
);

ALTER TABLE awb_delivery_route_relation ADD FOREIGN KEY (awb_id) REFERENCES awbs(id);
