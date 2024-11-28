CREATE SCHEMA day05;

DROP TABLE IF EXISTS day05.users CASCADE;
DROP TABLE IF EXISTS day05.chatrooms CASCADE;
DROP TABLE IF EXISTS day05.messages CASCADE;

CREATE TABLE day05.users
(
    id       BIGSERIAL PRIMARY KEY,
    login    VARCHAR,
    password VARCHAR
);

CREATE TABLE day05.chatrooms
(
    id     BIGSERIAL PRIMARY KEY,
    "name" VARCHAR,
    owner  BIGINT REFERENCES day05.users
);

CREATE TABLE day05.messages
(
    id        BIGSERIAL PRIMARY KEY,
    author    BIGINT REFERENCES day05.users,
    room      BIGINT REFERENCES day05.chatrooms,
    "text"    TEXT,
    date_time TIMESTAMP
);
