INSERT INTO users(login, password) VALUES ('user1', 'password1');
INSERT INTO users(login, password) VALUES ('user2', 'password2');
INSERT INTO users(login, password) VALUES ('user3', 'password3');
INSERT INTO users(login, password) VALUES ('user4', 'password4');
INSERT INTO users(login, password) VALUES ('user5', 'password5');

SELECT * FROM users;

INSERT INTO chatrooms (name, owner) VALUES ('chatroom1', 1);
INSERT INTO chatrooms (name, owner) VALUES ('chatroom2', 1);
INSERT INTO chatrooms (name, owner) VALUES ('chatroom3', 1);
INSERT INTO chatrooms (name, owner) VALUES ('chatroom4', 4);
INSERT INTO chatrooms (name, owner) VALUES ('chatroom5', 4);

SELECT * FROM chatrooms;

INSERT INTO messages (author, room, "text", date_time) VALUES (2, 1, 'message1', '2021-01-01 00:00:00');
INSERT INTO messages (author, room, "text", date_time) VALUES (3, 1, 'message2', '2021-01-01 00:00:01');
INSERT INTO messages (author, room, "text", date_time) VALUES (1, 1, 'message3', '2021-01-01 00:00:02');
INSERT INTO messages (author, room, "text", date_time) VALUES (5, 1, 'message4', '2021-01-01 00:00:03');
INSERT INTO messages (author, room, "text", date_time) VALUES (5, 1, 'message5', '2021-01-01 00:00:04');
INSERT INTO messages (author, room, "text", date_time) VALUES (4, 4, 'message0', '2021-01-01 00:00:05');
INSERT INTO messages (author, room, "text", date_time) VALUES (4, 4, 'message000', '2021-01-01 00:00:06');

INSERT INTO messages (author, room, "text", date_time) VALUES (1, 2, 'eee', '2021-01-01 00:00:07');
INSERT INTO messages (author, room, "text", date_time) VALUES (1, 3, 'dddddddddddd', '2021-01-01 00:00:08');
INSERT INTO messages (author, room, "text", date_time) VALUES (5, 3, 'eeeeeeee', '2021-01-01 00:00:09');
INSERT INTO messages (author, room, "text", date_time) VALUES (1, 5, 'qweqw', '2021-01-01 00:00:10');



SELECT * FROM messages;
