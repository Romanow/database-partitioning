-- V 1.00 create tables

CREATE SEQUENCE seq_users_generator INCREMENT 1;

CREATE TABLE users
(
    id         INT PRIMARY KEY DEFAULT NEXTVAL('seq_users_generator'),
    login      VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL
);