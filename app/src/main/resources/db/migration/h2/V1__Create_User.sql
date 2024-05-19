CREATE TABLE users (
    id IDENTITY PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(500) NOT NULL,
    password VARCHAR(1000) NOT NULL
);