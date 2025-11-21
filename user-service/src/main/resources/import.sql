INSERT INTO users (id, username, email) VALUES (1, 'alice', 'alice@test.com');
INSERT INTO users (id, username, email) VALUES (2, 'bob', 'bob@test.com');
INSERT INTO users (id, username, email) VALUES (3, 'admin', 'admin@test.com');
ALTER SEQUENCE users_SEQ RESTART WITH 4;