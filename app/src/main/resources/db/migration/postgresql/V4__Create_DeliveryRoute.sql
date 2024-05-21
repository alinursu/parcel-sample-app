CREATE TABLE delivery_routes (
    id SERIAL PRIMARY KEY,
    delivery_man_id INTEGER NOT NULL,
    delivery_date DATE NOT NULL,
    CONSTRAINT fk_delivery_routes_users FOREIGN KEY (delivery_man_id) REFERENCES users(id)
);

ALTER TABLE awb_delivery_route_relation ADD CONSTRAINT fk_relation_delivery_routes FOREIGN KEY (delivery_route_id) REFERENCES delivery_routes(id);
