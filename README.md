# Donaton

Sistema inteligente para la gestión y coordinación de ayuda humanitaria desarrollado con Spring Boot y arquitectura basada en capas.

---

# Descripción

Donaton es una plataforma diseñada para optimizar la administración de donaciones, inventario y ayuda humanitaria en situaciones de emergencia.

El sistema permite gestionar:

- Donaciones
- Usuarios
- Inventario
- Coordinación logística
- Panel administrativo

La solución fue desarrollada utilizando buenas prácticas de arquitectura de software, patrones de diseño y control de versiones profesional.

---

# Tecnologías utilizadas

## Backend

- Java 17
- Spring Boot
- Spring MVC
- Spring Data JPA
- H2 Database

## Frontend

- Thymeleaf
- HTML5
- CSS3

## Testing

- JUnit 5
- Mockito

## Control de versiones

- Git
- GitHub

---

# Arquitectura del sistema

El proyecto implementa una arquitectura MVC desacoplada basada en capas:

Frontend (Thymeleaf)
↓
Controllers
↓
Services
↓
Repositories
↓
Base de datos H2

---

# Patrones de diseño implementados

## Repository Pattern

Permite desacoplar el acceso a datos de la lógica de negocio.

Beneficios:

- Bajo acoplamiento
- Mayor mantenibilidad
- Separación de responsabilidades

---

## Service Layer Pattern

Centraliza la lógica de negocio de la aplicación.

Beneficios:

- Reutilización de lógica
- Escalabilidad
- Código organizado

---

## DTO Pattern

Permite transferir datos entre capas sin exponer directamente las entidades.

Beneficios:

- Seguridad
- Desacoplamiento
- Mejor control de datos

---

# Estrategia Git

El proyecto utiliza una estrategia basada en Git Flow:

- main
- develop
- feature/modelos
- feature/services
- feature/controllers
- feature/frontend
- feature/testing
- feature/docs

Esto permitió una gestión organizada del desarrollo y evidencia de merges y ramas.

---

# Pruebas unitarias

Se implementaron pruebas unitarias utilizando:

- JUnit 5
- Mockito

Las pruebas validan la lógica de negocio sin depender de la base de datos real.

---

# Características principales

- Dashboard administrativo
- CRUD de donaciones
- Arquitectura desacoplada
- Base de datos integrada H2
- Frontend profesional
- API REST
- Pruebas unitarias

---

# Cómo ejecutar el proyecto

## Clonar repositorio

```bash
git clone https://github.com/Robinson-duoc/donaton.git
```

## Ejecutar proyecto

```bash
./mvnw spring-boot:run
```

---

# Accesos

## Dashboard

http://localhost:8080

## H2 Console

http://localhost:8080/h2-console

Configuración:

- JDBC URL: jdbc:h2:mem:donatondb
- User: sa
- Password:

---

# Autor

Proyecto desarrollado para evaluación semestral de Arquitectura y Desarrollo de Software.