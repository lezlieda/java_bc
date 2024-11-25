INSERT INTO "users"(username, password)
VALUES ('admin', 'admin'),
         ('user', 'user'),
         ('guest', 'guest'),
         ('root', 'root'),
         ('superuser', 'superuser');

INSERT INTO "chatrooms"(name, owner)
VALUES ('room1', 1),
       ('room2', 1),
       ('room3', 1),
       ('room4', 3),
       ('room5', 3);

INSERT INTO "messages" (user_id, chatroom_id, message)
VALUES (1, 1, 'Hello World!'),
         (2, 1, 'Hello User!'),
         (1, 1, 'Wuzup?'),
         (2, 1, 'Not really'),
         (1, 1, 'ORLY?');
