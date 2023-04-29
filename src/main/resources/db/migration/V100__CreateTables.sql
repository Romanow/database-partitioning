-- V 1.00 create tables

CREATE SEQUENCE seq_users_generator INCREMENT 1;

CREATE TABLE users
(
    id                 INT DEFAULT nextval('seq_users_generator'),
    login              VARCHAR(255) NOT NULL,
    created_date       TIMESTAMP    NOT NULL,
    last_modified_date TIMESTAMP    NOT NULL
) PARTITION BY RANGE (created_date);

CREATE TABLE users_2023_01 PARTITION OF users FOR VALUES FROM ('2023-01-01') TO ('2023-02-01');
CREATE TABLE users_2023_02 PARTITION OF users FOR VALUES FROM ('2023-02-01') TO ('2023-03-01');
CREATE TABLE users_2023_03 PARTITION OF users FOR VALUES FROM ('2023-03-01') TO ('2023-04-01');
CREATE TABLE users_2023_04 PARTITION OF users FOR VALUES FROM ('2023-04-01') TO ('2023-05-01');
CREATE TABLE users_2023_05 PARTITION OF users FOR VALUES FROM ('2023-05-01') TO ('2023-06-01');
