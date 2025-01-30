CREATE TABLE link
(
    id SERIAL PRIMARY KEY,
    url TEXT NOT NULL,
    short_code TEXT NOT NULL UNIQUE,
    generated_link TEXT NOT NULL
);

CREATE INDEX idx_link_url ON link (url);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    firstname VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255)
);

CREATE TABLE token (
    id SERIAL PRIMARY KEY,
    expired BOOLEAN NOT NULL,
    revoked BOOLEAN NOT NULL,
    token VARCHAR(255),
    token_type VARCHAR(255),
    user_id INTEGER REFERENCES users(id)
);