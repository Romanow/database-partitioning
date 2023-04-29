# Database table partitioning

[![Build project](https://github.com/Romanow/database-partitioning/actions/workflows/build.yml/badge.svg?branch=master)](https://github.com/Romanow/database-partitioning/actions/workflows/build.yml)

## Задача

Реализовать партиционирование таблиц по месяцам на основе поля `createdDate`.

## Реализация

Используем партициоривание в [Postrges](https://www.postgresql.org/docs/13/ddl-partitioning.html).

```sql
SELECT * FROM users;

INSERT INTO users (login, created_date, last_modified_date)
VALUES ('login', '2023-04-30', NOW());

SELECT * FROM users;

DROP TABLE users_2023_04;

SELECT * FROM users;
```