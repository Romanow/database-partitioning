-- file: 10-create-user-and-db.sql
CREATE USER program WITH PASSWORD 'test';

CREATE DATABASE service;
GRANT ALL PRIVILEGES ON DATABASE service TO program;
