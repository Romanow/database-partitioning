# Database table partitioning

[![Build project](https://github.com/Romanow/database-partitioning/actions/workflows/build.yml/badge.svg?branch=master)](https://github.com/Romanow/database-partitioning/actions/workflows/build.yml)

## Задача

Реализовать партиционирование таблиц по месяцам на основе поля `createdDate`.

## Реализация

Используем партициоривание в [Postrges](https://www.postgresql.org/docs/13/ddl-partitioning.html).

Выключаем валидацию схемы:

```yaml
spring:
  jpa:
    hibernate.ddl-auto: none
```