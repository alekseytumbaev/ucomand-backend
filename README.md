# UCOMAND backend

## Требования

- [docker](https://docs.docker.com/desktop/install/windows-install/)

## Запуск

Откройте терминал в корне проекта, выполните `docker сompose up`

## Использование

Для того чтобы посмотреть апи проекта перейдите на http://localhost:8080/swagger-ui

Чтобы получить токен рут пользователя вызовите `GET /users/rootToken`

Для получения тегов, созданных во время инициализации, вызовите `GET /tags`

## Работа с базой данных

### Postgres

Чтобы использовать Postgres в файле `.env` поменяйте занчение переменной `SPRING_PROFILES_ACTIVE` на `ift`
и пересоберите контейнер.

Параметры для подключения:

- хост: localhost
- порт: 5432
- база: ucomand-db
- пользователь: ucomand
- пароль: ucomand

### In-memory

Чтобы использовать in-memory базу в файле `.env` поменяйте занчение переменной `SPRING_PROFILES_ACTIVE` на `dev`
и пересоберите контейнер.

**Важно**: контейнер с базой Postgres выключать нельзя даже если используете in-memory.

Для подключения к базе:

- перейдите на http://localhost:8080/h2-console
- Driver Class: org.h2.Driver
- JDBC URL: jdbc:h2:mem:ucomand-db
- User Name: ucomand
- Password: ucomand
