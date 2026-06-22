# Donatón

Sistema web para la gestión de donaciones, usuarios e inventario desarrollado con arquitectura modular usando Spring Boot.

## Descripción

Donatón es una plataforma que permite administrar campañas de donación mediante tres módulos principales:

* Gestión de usuarios
* Gestión de inventario
* Gestión de donaciones

El objetivo es centralizar el registro y seguimiento de aportes realizados por usuarios y controlar los recursos disponibles.

---

## Tecnologías utilizadas

### Backend

* Java 17
* Spring Boot 4
* Spring MVC
* Spring Data JPA
* Maven

### Base de datos

* MySQL

### Frontend

* Thymeleaf
* HTML
* CSS
* JavaScript

### Testing

* JUnit 5
* Mockito

---

## Arquitectura del proyecto

```plaintext
src
├── main
│   ├── java
│   │   └── com.donaton
│   │       ├── usuarios
│   │       ├── inventario
│   │       ├── donaciones
│   │       └── DonatonApplication.java
│   │
│   └── resources
│       ├── templates
│       ├── static
│       └── application.properties
│
└── test
    └── java
        └── com.donaton
            ├── usuarios
            ├── inventario
            └── donaciones
```

---

## Funcionalidades

### Usuarios

* Registro de usuarios
* Validación de correo duplicado
* Consulta de usuarios
* Búsqueda por ID

### Inventario

* Registro de productos
* Validación de usuario existente
* Control de cantidad

### Donaciones

* Registro de donaciones
* Validación de usuario
* Cálculo del total donado
* Historial de donaciones

---

## Instalación

### 1. Clonar repositorio

```bash
git clone URL_DEL_REPOSITORIO
```

Entrar al proyecto:

```bash
cd donaton
```

---

### 2. Configurar base de datos

Crear una base de datos:

```sql
CREATE DATABASE donaton;
```

Configurar:

`src/main/resources/application.properties`

Ejemplo:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/donaton
spring.datasource.username=root
spring.datasource.password=tu_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

### 3. Instalar dependencias

```bash
mvn clean install
```

---

### 4. Ejecutar aplicación

```bash
mvn spring-boot:run
```

Aplicación disponible en:

```plaintext
http://localhost:8080
```

---

## Ejecutar pruebas

Ejecutar todas:

```bash
mvn test
```

Ejecutar una clase:

```bash
mvn -Dtest=UsuarioServiceTest test
```

---

## Casos de prueba implementados

### UsuarioService

* Registro exitoso
* Validación correo duplicado
* Obtener usuarios
* Buscar por ID

### InventarioService

* Registro exitoso
* Usuario inexistente
* Cantidad inválida
* Obtener inventario

### DonacionService

* Registro exitoso
* Usuario inexistente
* Monto inválido
* Obtener total donado

---

