DROP TABLE IF EXISTS "users" CASCADE;

CREATE TABLE "users"
(
    id       BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    username VARCHAR(255),
    password VARCHAR(255)
);

DROP TABLE IF EXISTS "messages" CASCADE;

CREATE TABLE "messages"
(
    id      BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    user_id BIGINT REFERENCES "users" (id) ON DELETE CASCADE ON UPDATE CASCADE,
    message VARCHAR(4096),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
