# Migrate Postgres from 12 to 14

Установка Postgres 12

```shell
$ sudo sh -c 'echo "deb http://apt.postgresql.org/pub/repos/apt $(lsb_release -cs)-pgdg main" > /etc/apt/sources.list.d/pgdg.list'

$ wget --quiet -O - https://www.postgresql.org/media/keys/ACCC4CF8.asc | sudo apt-key add -

$ sudo apt-get update

$ sudo apt-get -y install postgresql-12 postgresql-client-12

```

Авторизуемся под postgres с локальной машины:

```shell
$ sudo su postgres
$ psql
```

Создаем пользователя `program`:`test` и базу данных `service`:

```sql
CREATE DATABASE service;
CREATE USER program WITH PASSWORD 'test';
GRANT ALL PRIVILEGES ON DATABASE service TO program;
```

Авторизуемся в бд `service` от имени `program`:

```shell
$ psql -h localhost -p 5432 -U program service
```

Создаем таблицу `users` и партиции:

```sql
CREATE SEQUENCE seq_users_generator INCREMENT 1;

CREATE TABLE users
(
    id                 INT DEFAULT NEXTVAL('seq_users_generator'),
    login              VARCHAR(255) NOT NULL,
    created_date       TIMESTAMP    NOT NULL,
    last_modified_date TIMESTAMP    NOT NULL
) PARTITION BY RANGE (created_date);

CREATE TABLE users_2023_01 PARTITION OF users FOR VALUES FROM ('2023-01-01') TO ('2023-02-01');
CREATE TABLE users_2023_02 PARTITION OF users FOR VALUES FROM ('2023-02-01') TO ('2023-03-01');
CREATE TABLE users_2023_03 PARTITION OF users FOR VALUES FROM ('2023-03-01') TO ('2023-04-01');
CREATE TABLE users_2023_04 PARTITION OF users FOR VALUES FROM ('2023-04-01') TO ('2023-05-01');
CREATE TABLE users_2023_05 PARTITION OF users FOR VALUES FROM ('2023-05-01') TO ('2023-06-01');
```

Если требуется подключаться с локальной машины, то в конфигурации postgres добавить:

```shell
$ echo "host  all  all  0.0.0.0/0  md5" | tee -a /etc/postgresql/12/main/pg_hba.conf
$ sudo sed -i "s/#listen_addresses = 'localhost'*/listen_addresses = '*'/" /etc/postgresql/12/main/postgresql.conf
```