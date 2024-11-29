DROP SCHEMA IF EXISTS day08 CASCADE;

CREATE SCHEMA day08;


CREATE TABLE day08.users
(
    id       BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);
