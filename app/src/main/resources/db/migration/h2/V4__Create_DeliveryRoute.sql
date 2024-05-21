CREATE TABLE delivery_routes (
    id IDENTITY PRIMARY KEY,
    delivery_man_id INTEGER NOT NULL,
    delivery_date DATE NOT NULL,
    FOREIGN KEY (delivery_man_id) REFERENCES users(id)
);

ALTER TABLE awb_delivery_route_relation ADD FOREIGN KEY (delivery_route_id) REFERENCES delivery_routes(id);
