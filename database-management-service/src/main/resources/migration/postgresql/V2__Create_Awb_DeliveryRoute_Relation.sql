CREATE TABLE awb_delivery_route_relation(
    awb_id INTEGER NOT NULL,
    delivery_route_id INTEGER NOT NULL,
    PRIMARY KEY(awb_id, delivery_route_id)
);