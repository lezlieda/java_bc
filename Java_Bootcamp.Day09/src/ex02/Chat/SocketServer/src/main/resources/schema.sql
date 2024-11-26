DROP TABLE IF EXISTS "users" CASCADE;

CREATE TABLE "users"
(
    id       BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    username VARCHAR(255),
    password VARCHAR(255)
);

DROP TABLE IF EXISTS "chatrooms" CASCADE;

CREATE TABLE "chatrooms"
(
    id    BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name  VARCHAR(255),
    owner BIGINT REFERENCES "users"
);

DROP TABLE IF EXISTS "messages" CASCADE;

CREATE TABLE "messages"
(
    id      BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    chatroom_id BIGINT REFERENCES "chatrooms" (id) ON DELETE CASCADE ON UPDATE CASCADE,
    user_id BIGINT REFERENCES "users" (id) ON DELETE CASCADE ON UPDATE CASCADE,
    message VARCHAR(4096),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);