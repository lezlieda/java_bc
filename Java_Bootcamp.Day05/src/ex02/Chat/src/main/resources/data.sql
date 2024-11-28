INSERT INTO day05.users(login, password)
VALUES ('user1', 'password1');
INSERT INTO day05.users(login, password)
VALUES ('user2', 'password2');
INSERT INTO day05.users(login, password)
VALUES ('user3', 'password3');
INSERT INTO day05.users(login, password)
VALUES ('user4', 'password4');
INSERT INTO day05.users(login, password)
VALUES ('user5', 'password5');

SELECT *
FROM day05.users;

INSERT INTO day05.chatrooms (name, owner)
VALUES ('chatroom1', 1);
INSERT INTO day05.chatrooms (name, owner)
VALUES ('chatroom2', 1);
INSERT INTO day05.chatrooms (name, owner)
VALUES ('chatroom3', 1);
INSERT INTO day05.chatrooms (name, owner)
VALUES ('chatroom4', 4);
INSERT INTO day05.chatrooms (name, owner)
VALUES ('chatroom5', 4);

SELECT *
FROM day05.chatrooms;

INSERT INTO day05.messages (author, room, "text", date_time)
VALUES (2, 1, 'message1', '2021-01-01 00:00:00');
INSERT INTO day05.messages (author, room, "text", date_time)
VALUES (3, 1, 'message2', '2021-01-01 00:00:01');
INSERT INTO day05.messages (author, room, "text", date_time)
VALUES (1, 1, 'message3', '2021-01-01 00:00:02');
INSERT INTO day05.messages (author, room, "text", date_time)
VALUES (5, 1, 'message4', '2021-01-01 00:00:03');
INSERT INTO day05.messages (author, room, "text", date_time)
VALUES (5, 1, 'message5', '2021-01-01 00:00:04');
INSERT INTO day05.messages (author, room, "text", date_time)
VALUES (4, 4, 'message0', '2021-01-01 00:00:05');
INSERT INTO day05.messages (author, room, "text", date_time)
VALUES (4, 4, 'message000', '2021-01-01 00:00:06');

SELECT *
FROM day05.messages;
