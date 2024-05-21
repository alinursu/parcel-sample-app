CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(500) NOT NULL,
    password VARCHAR(1000) NOT NULL
);

CREATE TABLE user_roles (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    role VARCHAR(20) NOT NULL,
    CONSTRAINT fk_user_roles_users FOREIGN KEY (user_id) REFERENCES users(id)
);
