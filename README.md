##1. Prerrequisitos y Configuración

Antes de levantar el proyecto, asegúrate de tener instalado en tu entorno local:
* **Java JDK 17** o superior.
* **Maven 3.6+** (el proyecto incluye el wrapper `mvnw`, por lo que no necesitas instalar Maven globalmente).
* Un editor de código o IDE como **IntelliJ IDEA**, **Eclipse** o **VS Code** con el plugin de Spring Boot.

Clona el repositorio y navega hasta la raíz del proyecto:

2. Comandos para Levantar el Proyecto
Dado que el proyecto está estructurado en módulos funcionales (usuarios, donaciones, inventario), puedes levantar la aplicación completa ejecutando el plugin de Spring Boot desde la raíz:

# En sistemas Unix/Linux/Mac
./mvnw spring-boot:run

# En Windows (PowerShell o CMD)
mvnw.cmd spring-boot:run

Una vez levantado, el servidor estará escuchando en el puerto configurado (por defecto 8080). Puedes acceder a la interfaz web de Thymeleaf desde tu navegador en: http://localhost:8080/usuarios (o la ruta raíz configurada).

3. Ejecución de Pruebas (Testing)
Para garantizar la calidad del software y cumplir con el aseguramiento de calidad (QA) y cobertura de más del 60% requerida, utilizamos JUnit 5 y Mockito.

Comando para ejecutar todos los tests:
Bash
# Unix/Linux/Mac
./mvnw test

# Windows
mvnw.cmd test

Dónde Ver los Resultados de los Tests
Una vez ejecutado el comando ./mvnw test, el reporte de ejecución se genera automáticamente en el sistema de compilación de Maven.

Reporte de consola: Puedes ver qué pruebas pasaron y cuáles fallaron directamente en la terminal.

Reportes detallados (XML y Texto): Los archivos generados por Surefire se encuentran en la ruta:
target/surefire-reports/

Reporte de Cobertura (JaCoCo): Si tienes configurado el plugin de cobertura JaCoCo en tu pom.xml, el archivo HTML que grafica la cobertura de código (para verificar el >60%) se genera en:
target/site/jacoco/index.html (puedes abrir este archivo directamente en tu navegador).


Estructura del Proyecto
El proyecto sigue una arquitectura organizada por dominios para mantener desacoplados los microservicios:

Plaintext
src/
├── main/
│   └── java/
│       └── com/donaton/
│           ├── donaciones/     # Módulo de donaciones (monetarias)
│           ├── inventario/     # Módulo de bodega e insumos físicos
│           └── usuarios/       # Módulo de gestión de donantes y usuarios
│               ├── controller/ # Puntos de entrada HTTP / Vistas Thymeleaf
│               ├── model/      # Entidades / Mapeo de Base de Datos
│               ├── repository/ # Interfaces de acceso a datos (JPA)
│               └── service/    # Lógica de negocio
└── test/                       # Espejo de la estructura main con las pruebas unitarias


Gestión de Dependencias y Plugins (Pom.xml)
El proyecto incluye las siguientes dependencias principales en el pom.xml:

spring-boot-starter-data-jpa

spring-boot-starter-thymeleaf

spring-boot-starter-web

h2 (Base de datos en memoria para desarrollo rápido y pruebas)

spring-boot-starter-test (Incluye JUnit 5, Spring Boot Test)

mockito-junit-jupiter

