# Donaton

Sistema de gestión de donaciones, inventario y usuarios, construido con **Spring Boot 4**, **Java 17**, **Maven**, **Spring Data JPA**, **MySQL** y **Thymeleaf**.

## Requisitos

- [Docker](https://www.docker.com/products/docker-desktop/)
- [Docker Compose](https://docs.docker.com/compose/) (incluido en Docker Desktop)

No necesitas instalar Java, Maven ni MySQL de forma local: todo corre dentro de contenedores.

## Puesta en marcha

1. Clona el repositorio:
   ```bash
   git clone <url-del-repositorio>
   cd donaton
   ```

2. Crea tu archivo de variables de entorno a partir de la plantilla:
   ```bash
   cp .env.example .env
   ```

3. Abre `.env` y cambia las contraseñas (`MYSQL_ROOT_PASSWORD`, `MYSQL_PASSWORD`) por unas propias. **Nunca subas tu `.env` real a git.**

4. Levanta la aplicación y la base de datos:
   ```bash
   docker-compose up --build
   ```

5. Abre tu navegador en:
   ```
   http://localhost:8080
   ```
   (o el puerto que hayas definido en `APP_PORT` dentro de `.env`)

## Comandos útiles

| Acción | Comando |
|---|---|
| Levantar en segundo plano | `docker-compose up -d --build` |
| Ver logs de la app | `docker-compose logs -f app` |
| Ver logs de la base de datos | `docker-compose logs -f db` |
| Detener los contenedores | `docker-compose down` |
| Detener y borrar los datos de la BD | `docker-compose down -v` |
| Reconstruir sin cache | `docker-compose build --no-cache` |

## Estructura del proyecto

```
src/main/java/com/donaton
├── config
├── donaciones      (controller, model, repository, service)
├── inventario      (controller, model, repository, service)
├── usuarios        (controller, model, repository, service)
└── DonatonApplication.java

src/main/resources
├── templates       (vistas Thymeleaf)
├── static
└── application.properties
```

## Variables de entorno

Definidas en `.env` (ver `.env.example` para la plantilla):

| Variable | Descripción | Valor por defecto |
|---|---|---|
| `APP_PORT` | Puerto expuesto de la aplicación | `8080` |
| `DB_PORT` | Puerto expuesto de MySQL | `3306` |
| `MYSQL_ROOT_PASSWORD` | Contraseña del usuario root de MySQL | — |
| `MYSQL_DATABASE` | Nombre de la base de datos | `donaton` |
| `MYSQL_USER` | Usuario de la aplicación para MySQL | `donaton_user` |
| `MYSQL_PASSWORD` | Contraseña del usuario de la aplicación | — |

## Notas

- Los datos de MySQL se persisten en un volumen de Docker (`donaton-data`), por lo que no se pierden al detener los contenedores (solo se pierden con `docker-compose down -v`).
- `spring.jpa.hibernate.ddl-auto=update` está configurado para crear/actualizar el esquema automáticamente en cada arranque. Para producción, se recomienda cambiarlo por migraciones controladas (por ejemplo, con Flyway o Liquibase).