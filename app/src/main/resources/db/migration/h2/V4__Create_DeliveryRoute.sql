CREATE TABLE delivery_routes (
    id IDENTITY PRIMARY KEY,
    delivery_man_id INTEGER NOT NULL,
    delivery_date DATE NOT NULL,
    delivery_address VARCHAR(1000) NOT NULL,
    ordered_by INTEGER NOT NULL,
    created_at DATE NOT NULL,
    last_updated_at DATE NOT NULL,
    status VARCHAR(50) NOT NULL,
    price REAL NOT NULL,
    FOREIGN KEY (delivery_man_id) REFERENCES users(id)
);

ALTER TABLE awb_delivery_route_relation ADD FOREIGN KEY (delivery_route_id) REFERENCES delivery_routes(id);