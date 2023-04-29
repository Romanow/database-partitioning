# Database table partitioning

## Задача

Реализовать партиционирование таблиц по месяцам на основе поля `createdDate`.

Требования:

1. Запрос сущности по `ID`.
2. Запрос сущности по `ID` с указанием партиции (`createdDate`).
3. Создание новой сущности в нужной партиции.
4. Выборка всех записей в партиции.

План:

1. Таблицы называть `users_<month>_<year>`, дату брать из `createdDate`.
2. При поиске по ID нужно динамически определять имя целевой таблицы `users` по диапазону ключей:
    * `name` – имя `<month>_<year>`;
    * `start` – начала диапазона периода.
    ```sql
    CREATE TABLE ranges
    (
        name  VARCHAR(8) PRIMARY KEY,
        start INT NOT NULL
    );
    ```
3. При создании новой записи добавлять запись в таблицу `ranges`, если этой записи там еще нет.
4. Внедрить этот механизм в `CrudRepository`.

## Реализация

Используем партициоривание в [Postrges](https://www.postgresql.org/docs/13/ddl-partitioning.html).

Выключаем валидацию схемы:

```yaml
spring:
  jpa:
    hibernate.ddl-auto: none
```