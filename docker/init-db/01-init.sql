CREATE DATABASE keycloak;
CREATE DATABASE catalog_db;
CREATE DATABASE playlist_db;
CREATE USER keycloak WITH ENCRYPTED PASSWORD 'password';
GRANT ALL PRIVILEGES ON DATABASE keycloak TO keycloak;
-- Для спрощення сервіси будуть ходити під postgres/password,
-- але в реальному проді треба окремі юзери.