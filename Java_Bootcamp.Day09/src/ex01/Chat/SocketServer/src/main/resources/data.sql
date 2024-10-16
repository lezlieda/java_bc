INSERT INTO "users"(username, password)
VALUES ('admin', 'admin'),
         ('user', 'user'),
         ('guest', 'guest'),
         ('root', 'root'),
         ('superuser', 'superuser');

INSERT INTO "messages" (user_id, message, created_at)
VALUES (0, 'Hello World!', '2019-01-01 00:00:00'),
         (1, 'Hello User!', '2019-01-01 00:00:01'),
         (2, 'Hello Guest!', '2019-01-01 00:00:02'),
         (3, 'Hello Root!', '2019-01-01 00:00:03'),
         (4, 'Hello Superuser!', '2019-01-01 00:00:04');