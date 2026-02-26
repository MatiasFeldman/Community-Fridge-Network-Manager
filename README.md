# Community Fridge Network Manager

Sistema de gestión de heladeras comunitarias desarrollado para una ONG que busca combatir el hambre y reducir el desperdicio de alimentos. La plataforma conecta colaboradores, personas en situación de vulnerabilidad alimentaria y técnicos de mantenimiento en torno a una red de heladeras solidarias distribuidas geográficamente.

> Trabajo Práctico Anual — Diseño de Sistemas (K3053) — UTN FRBA — 2024

---

## Descripción del Proyecto

La aplicación permite a una organización sin fines de lucro gestionar una red de heladeras comunitarias donde colaboradores pueden donar viandas, dinero o productos, y personas en situación vulnerable pueden retirar alimentos mediante tarjetas de acceso. El sistema monitorea en tiempo real el estado de las heladeras (temperatura, cantidad de viandas, incidentes) mediante sensores IoT conectados por MQTT, y coordina técnicos para la resolución de fallas.

### Funcionalidades principales

- **Gestión de colaboradores**: registro de personas humanas y jurídicas con distintas formas de colaboración (donación de viandas, dinero, distribución de viandas, hacerse cargo de heladeras, ofrecer productos/servicios canjeables por puntos)
- **Red de heladeras**: alta, baja y modificación de heladeras con geolocalización en mapa interactivo, monitoreo de temperatura y capacidad
- **Sensores IoT / MQTT**: recepción de datos de temperatura y solicitudes de apertura vía broker MQTT (Eclipse Paho)
- **Tarjetas de acceso**: sistema de tarjetas para personas vulnerables que habilitan el retiro de viandas con límite diario
- **Sistema de suscripciones**: alertas configurables por heladera (pocas viandas, muchas viandas, desperfecto) con notificación multicanal
- **Notificaciones multicanal**: email (JavaMail), WhatsApp (Twilio), Telegram Bot API
- **Reportes automáticos**: generación periódica de reportes en PDF (iTextPDF) sobre fallas, viandas donadas y movimientos por heladera, con ejecución programada vía Quartz Scheduler
- **Carga masiva de colaboradores**: importación desde archivos CSV (OpenCSV)
- **Recomendación de puntos de donación**: integración con API de georreferenciación (GeoRef Argentina / GeoRef CABA) para sugerir ubicaciones óptimas
- **Canje de puntos**: marketplace de productos y servicios ofrecidos por colaboradores, canjeables por puntos acumulados
- **Gestión de incidentes**: reporte de fallas técnicas y alertas automáticas por temperatura, con asignación de técnicos por cercanía
- **Sistema de usuarios con roles y permisos**: autenticación, autorización y SSO (Auth0)
- **Visitas técnicas**: registro de visitas a heladeras con evidencia fotográfica

## Stack Tecnológico

| Capa | Tecnología |
|------|-----------|
| Lenguaje | Java 17 |
| Build Tool | Maven 3.8+ |
| Web Framework | Javalin 6.3 |
| Template Engine | Handlebars (jknack) |
| ORM / Persistencia | JPA + Hibernate (jpa-extras) |
| Base de Datos | MySQL 8 |
| Mensajería IoT | MQTT (Eclipse Paho) |
| HTTP Client | Retrofit 2 + Gson |
| Notificaciones | JavaMail, Twilio (WhatsApp), Telegram Bot API |
| Reportes PDF | iTextPDF 5 |
| Scheduling | Quartz Scheduler |
| CSV Parsing | OpenCSV |
| Utilidades | Lombok, Jackson, dotenv-java |
| Testing | JUnit 5, Mockito, HSQLDB (in-memory) |
| Code Quality | Checkstyle (Google), SpotBugs, JaCoCo (60% cobertura) |
| Autenticación | Auth0 SSO (PoC con Spring Security) |

## Arquitectura

El proyecto sigue una arquitectura **MVC** con las siguientes capas:

```
src/main/java/ar/edu/utn/frba/dds/
├── controllers/        # Controladores HTTP (Javalin handlers)
├── dtos/               # Data Transfer Objects por dominio
├── exceptions/         # Excepciones de negocio organizadas por módulo
├── main/               # Entry points (App, CronTask_Reportes)
├── middlewares/         # Auth middleware y middlewares de la app
├── models/
│   ├── entities/       # Entidades de dominio JPA
│   │   ├── colaboraciones/    # Donaciones, distribuciones, canjes, carga masiva
│   │   ├── heladeras_y_viandas/  # Heladeras, viandas, sensores, incidentes
│   │   ├── personas/          # Colaboradores humanos y jurídicos
│   │   ├── suscripciones/     # Suscripciones y tipos de alerta
│   │   ├── tecnicos/          # Técnicos y visitas a heladeras
│   │   ├── usuarios/          # Usuarios, roles y permisos
│   │   ├── reportes/          # Generación de reportes
│   │   └── helpers/           # Mensajería, geolocalización, JSON, etc.
│   ├── repositories/   # Repositorios con patrón DAO
│   └── factories/      # Factories para creación de entidades
├── server/             # Configuración de servidor, router y handlers de error
├── services/           # Servicios externos (GeoRef, MQTT, API integraciones)
├── converter/          # Converters JPA para enums
└── utils/              # Seguridad de contraseñas, permisos, utilidades
```

**Frontend**: templates Handlebars (.hbs) con CSS y JavaScript vanilla, incluyendo mapas interactivos para geolocalización de heladeras.

## Requisitos

- **Java**: JDK 17+
- **Maven**: 3.8.1+
- **MySQL**: 8.0+
- **Broker MQTT**: para comunicación con sensores de heladeras

## Instalación y Ejecución

```bash
# Clonar el repositorio
git clone https://github.com/mfeldacc/Community-Fridge-Network-Manager.git
cd Community-Fridge-Network-Manager

# Configurar variables de entorno
# Crear archivo .env o editar config.properties con credenciales de:
#   - Base de datos MySQL
#   - Twilio (WhatsApp)
#   - Telegram Bot Token
#   - API Keys de georreferenciación
#   - Auth0 (SSO)

# Compilar y ejecutar tests
mvn clean verify

# Ejecutar la aplicación
mvn exec:java -Dexec.mainClass="ar.edu.utn.frba.dds.server.App"
```

## Testing

```bash
# Ejecutar tests unitarios
mvn test

# Validación exhaustiva (tests + checkstyle + spotbugs + cobertura)
mvn clean verify
```

Los tests utilizan **HSQLDB** como base de datos in-memory y **Mockito** para mocking de servicios externos.

## Documentación

La carpeta `docs/` contiene:

- Diagramas de clases (StarUML `.mdj`)
- Diagrama de casos de uso
- Investigación de APIs de integración
- Justificación de decisiones de diseño

## Equipo

Grupo 21 — Cursada 2024, turno mañana

---

*Proyecto académico desarrollado como Trabajo Práctico Anual para la materia Diseño de Sistemas (K3053) de la Universidad Tecnológica Nacional — Facultad Regional Buenos Aires.*
