DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS chatrooms CASCADE;
DROP TABLE IF EXISTS messages CASCADE;

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    login VARCHAR,
    password VARCHAR
);

CREATE TABLE chatrooms (
    id BIGSERIAL PRIMARY KEY,
    "name" VARCHAR,
    owner BIGINT REFERENCES users
);

CREATE TABLE messages (
    id BIGSERIAL PRIMARY KEY,
    author BIGINT REFERENCES users,
    room BIGINT REFERENCES chatrooms,
    "text" TEXT,
    date_time TIMESTAMP
);
