# 🎁 Plataforma Avanzada Donatón - Sistema Integrado de Microservicios

Este repositorio contiene la solución tecnológica oficial para la plataforma **Donatón**. El ecosistema completo ha sido diseñado bajo los estándares más exigentes de la ingeniería de software, desacoplando la interfaz de usuario en un cliente reactivo (NPM) y la lógica de negocio en un motor distribuido de microservicios que exponen una API REST pura con persistencia relacional.

---

## 🏗️ Arquitectura y Estructura Global del Proyecto

El proyecto está organizado de forma modular e independiente para cumplir estrictamente con los requerimientos de empaquetado de componentes:

```text
donaton/
├── donaton-frontend/       # COMPONENTE FRONTEND (Estándar NPM)
│   ├── package.json        # Configuración de Node, scripts y dependencias
│   ├── vite.config.js      # Configuraciones del empaquetador Vite
│   ├── public/             # Recursos estáticos e íconos
│   └── src/                # Código fuente reactivo
│       ├── main.jsx        # Punto de entrada de React
│       └── App.jsx         # Tablero e interfaz integrada de la plataforma
│
├── src/                    # COMPONENTE BACKEND (Spring Boot / Java)
│   ├── main/java/com/...   # Lógica de Negocio, Controladores REST y Entidades
│   │   ├── controller/     # Endpoints de la API REST (`@RestController`)
│   │   ├── model/          # Entidades de persistencia (JPA)
│   │   ├── repository/     # Interfaces de acceso a datos (Spring Data JPA)
│   │   └── service/        # Capa analítica de negocio y validaciones
│   └── test/java/com/...   # Código de Pruebas Unitarias (JUnit 5 / Mockito)
│
├── pom.xml                 # Configuración central de Maven y Plugin JaCoCo
└── README.md               # Este manual de instrucciones unificado del proyecto
⚙️ Componente Backend (API REST & Persistencia)El backend utiliza Spring Boot 4.0.6, Java 17 y JPA / Hibernate. Los controladores devuelven recursos directos en formato JSON, eliminando dependencias de renderizado local y habilitando políticas globales de CORS para la intercomunicación de red.🗄️ 1. Configuración del Recurso de Persistencia (MySQL / XAMPP)Inicie los servicios de Apache y MySQL desde el panel de control de XAMPP.Diríjase a su gestor web en http://localhost/phpmyadmin/.Cree una base de datos vacía con el nombre exacto: donaton.Al levantar el servidor, el motor ORM de Hibernate generará automáticamente el esquema físico de las tablas (usuarios, donaciones, inventario) e índices relacionales.🚀 2. Ejecución de los MicroserviciosAbra una terminal en la raíz principal del proyecto y ejecute el comando del Maven Wrapper para levantar el servidor:PowerShell.\mvnw spring-boot:run
El motor backend quedará en ejecución escuchando peticiones en: http://localhost:8080.📊 3. Informe de Pruebas Unitarias y Cobertura (JaCoCo)El software cuenta con una suite de pruebas automatizadas con JUnit 5 y Mockito para auditar componentes críticos. Para correr los tests y compilar las métricas gráficas de control de calidad, ejecute:PowerShell.\mvnw test
Al finalizar con éxito (BUILD SUCCESS), se generará un reporte interactivo. Puede visualizar los gráficos de cobertura abriendo el archivo físico target/site/jacoco/index.html en cualquier navegador web.🎨 Componente Frontend (Estándar NPM)La interfaz gráfica del cliente está construida sobre React y gestionada de manera autónoma bajo el estándar NPM a través de Vite.🚀 Instrucciones de Instalación y Ejecución del ClienteAbra una segunda terminal y muévase directamente a la carpeta del frontend:Bashcd donaton-frontend
Instale los paquetes y dependencias de Node estructurados en el archivo package.json:Bashnpm install
Ejecute el script de desarrollo nativo para levantar la aplicación:Bashnpm run dev
Abra en su navegador la URL local indicada en la consola (usualmente http://localhost:5173) para interactuar con la plataforma interactiva de donaciones.🔗 Especificación de la API REST (Endpoints del Sistema)La comunicación e integración cruzada entre el componente frontend y el backend se realiza mediante llamadas HTTP asíncronas basadas en los siguientes endpoints oficiales:ComponenteTipo HTTPRuta RESTCarga Útil (JSON Body / Respuesta)Descripción del RecursoUsuariosPOST/api/usuarios{ "nombre", "email", "password" }Registra un nuevo donante validando correos duplicados.UsuariosGET/api/usuariosLista de objetos UsuarioRecupera el listado completo de usuarios registrados.DonacionesPOST/api/donaciones{ "usuarioId", "monto" }Procesa un aporte financiero vinculando lógicamente al usuario.DonacionesGET/api/donaciones/totalValor numérico puro (Double)Retorna la sumatoria matemática del pozo total acumulado.InventarioPOST/api/inventario{ "usuarioId", "nombreProducto", "categoria", "cantidad" }Registra la entrada física de insumos materiales a bodega.InventarioGET/api/inventarioLista de objetos ItemInventarioDespliega el stock de productos disponibles en tiempo real.