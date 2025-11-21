-- Очищення (на всяк випадок, для dev mode)
TRUNCATE TABLE Track RESTART IDENTITY CASCADE;

-- Легендарний Рок
INSERT INTO Track (id, title, artist, album) VALUES (1, 'Bohemian Rhapsody', 'Queen', 'A Night at the Opera');
INSERT INTO Track (id, title, artist, album) VALUES (2, 'Hotel California', 'Eagles', 'Hotel California');
INSERT INTO Track (id, title, artist, album) VALUES (3, 'Stairway to Heaven', 'Led Zeppelin', 'Led Zeppelin IV');
INSERT INTO Track (id, title, artist, album) VALUES (4, 'Smells Like Teen Spirit', 'Nirvana', 'Nevermind');
INSERT INTO Track (id, title, artist, album) VALUES (5, 'Sweet Child O'' Mine', 'Guns N'' Roses', 'Appetite for Destruction');

-- Поп Хіти
INSERT INTO Track (id, title, artist, album) VALUES (6, 'Billie Jean', 'Michael Jackson', 'Thriller');
INSERT INTO Track (id, title, artist, album) VALUES (7, 'Shape of You', 'Ed Sheeran', 'Divide');
INSERT INTO Track (id, title, artist, album) VALUES (8, 'Blinding Lights', 'The Weeknd', 'After Hours');
INSERT INTO Track (id, title, artist, album) VALUES (9, 'Rolling in the Deep', 'Adele', '21');
INSERT INTO Track (id, title, artist, album) VALUES (10, 'Bad Guy', 'Billie Eilish', 'When We All Fall Asleep...');

-- Класика та Джаз
INSERT INTO Track (id, title, artist, album) VALUES (11, 'Fly Me to the Moon', 'Frank Sinatra', 'It Might as Well Be Swing');
INSERT INTO Track (id, title, artist, album) VALUES (12, 'What a Wonderful World', 'Louis Armstrong', 'Single');
INSERT INTO Track (id, title, artist, album) VALUES (13, 'Take Five', 'Dave Brubeck', 'Time Out');

-- Електроніка
INSERT INTO Track (id, title, artist, album) VALUES (14, 'Get Lucky', 'Daft Punk', 'Random Access Memories');
INSERT INTO Track (id, title, artist, album) VALUES (15, 'Levels', 'Avicii', 'Levels');

-- Оновлення лічильника ID (важливо для Postgres!)
ALTER SEQUENCE Track_SEQ RESTART WITH 16;