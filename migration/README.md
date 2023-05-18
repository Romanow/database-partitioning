# Миграция Postgres 12 на 13

## Установка и настройка Postgres 12

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
$ echo "host  all  all  0.0.0.0/0  md5" | sudo tee -a /etc/postgresql/12/main/pg_hba.conf
$ sudo sed -i "s/#listen_addresses = 'localhost'*/listen_addresses = '*'/" /etc/postgresql/12/main/postgresql.conf
$ sudo systemctl restart postgresql@12-main.service
```

## Миграция на Postgres 13

```shell
# дамп данных (на всякий случай)
$ pg_dump -h localhost -p 5432 -U program -d service > service.dump

$ sudo pg_ctlcluster 12 main status
pg_ctl: server is running (PID: 5737)
/usr/lib/postgresql/12/bin/postgres "-D" "/var/lib/postgresql/12/main" "-c" "config_file=/etc/postgresql/12/main/postgresql.conf"

# устанавливаем новую версию Postgres 13
$ sudo apt-get install postgresql-13 postgresql-client-13 -y

$ pg_lsclusters
Ver Cluster Port Status Owner    Data directory              Log file
12  main    5432 online postgres /var/lib/postgresql/12/main /var/log/postgresql/postgresql-12-main.log
13  main    5433 online postgres /var/lib/postgresql/13/main /var/log/postgresql/postgresql-13-main.log

$ sudo lsof -i -P -n | grep LISTEN | grep postgres
postgres  10110        postgres    3u  IPv4  58474      0t0  TCP *:5432 (LISTEN)
postgres  10110        postgres    4u  IPv6  58475      0t0  TCP *:5432 (LISTEN)
postgres  12162        postgres    5u  IPv4  60237      0t0  TCP 127.0.0.1:5433 (LISTEN)

# Останавливаем и удаляем новый кластер Postgres 13
$ sudo pg_dropcluster 13 main --stop

$ pg_lsclusters
Ver Cluster Port Status Owner    Data directory              Log file
12  main    5432 online postgres /var/lib/postgresql/12/main /var/log/postgresql/postgresql-12-main.log

# Останавливаем текущий кластер Postgres 12
$ sudo systemctl stop postgresql@12-main.service

# Обновляем до Postgres 13 (который предварительно установлен)
$ sudo pg_upgradecluster -v 13 12 main

# Удаляем старый кластер
$ sudo pg_dropcluster 12 main --stop

$ sudo apt-get purge postgresql-12 postgresql-client-12
```