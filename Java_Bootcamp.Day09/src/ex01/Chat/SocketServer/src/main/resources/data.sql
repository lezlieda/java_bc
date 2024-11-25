INSERT INTO "users"(username, password)
VALUES ('admin', 'admin'),
         ('user', 'user'),
         ('guest', 'guest'),
         ('root', 'root'),
         ('superuser', 'superuser');

INSERT INTO "messages" (user_id, message)
VALUES (1, 'Hello World!'),
         (1, 'Hello User!'),
         (2, 'Hello Guest!'),
         (3, 'Hello Root!'),
         (4, 'Hello Superuser!');
