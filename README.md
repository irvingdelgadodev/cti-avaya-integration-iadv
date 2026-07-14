# CTI Integration - FullStack Technical Test

## 📋 Información del Proyecto

| Campo | Valor |
|-------|-------|
| **Proyecto** | CTI Integration con AVAYA |
| **Autor** | IRVING ALAIN DELGADO VALVERDE |
| **Fecha** | 2026 |
| **Versión** | 1.0.0 |
| **Tecnologías** | Java 17, Spring Boot 4.1.0, Angular 19 |

---

## 📖 Descripción

Prueba técnica para **Desarrollador FullStack Senior** que simula una integración con una plataforma telefónica **AVAYA AES** utilizando un Mock CTI Server basado en WebSockets.

### 🎯 Objetivo

- ✅ Integración CTI con AVAYA
- ✅ Procesamiento de eventos en tiempo real
- ✅ Manejo de WebSockets
- ✅ Arquitectura backend robusta
- ✅ Dashboard Angular en tiempo real

---

## 🏗️ Arquitectura
┌─────────────────────────────────────────────────────────────────┐
│ MOCK CTI SERVER │
│ (ws://precook-overtone-syndrome.ngrok-free.dev) │
└─────────────────────────┬───────────────────────────────────────┘
│ WebSocket
▼
┌─────────────────────────────────────────────────────────────────┐
│ BACKEND (Spring Boot) │
│ ┌─────────────┐ ┌─────────────┐ ┌─────────────────────────┐ │
│ │ WebSocket │ │ Estado │ │ REST API │ │
│ │ Client │──▶│ In-Memory │──▶│ /api/cti/* │ │
│ │ Reconexión │ │ Thread- │ │ - /health │ │
│ │ Automática │ │ Safe │ │ - /calls/active │ │
│ └─────────────┘ └─────────────┘ │ - /agents │ │
│ │ - /extensions │ │
│ └─────────────────────────┘ │
└─────────────────────────┬───────────────────────────────────────┘
│ HTTP (REST)
▼
┌─────────────────────────────────────────────────────────────────┐
│ FRONTEND (Angular) │
│ ┌─────────────────────────────────────────────────────────────┐│
│ │ Dashboard CTI ││
│ │ 📞 Llamadas Activas │ 👤 Agentes ││
│ │ ✅ Estadísticas │ 🟢 Conectado/Desconectado ││
│ │ 🔄 Actualización en tiempo real (Polling 2s) ││
│ └─────────────────────────────────────────────────────────────┘│
└─────────────────────────────────────────────────────────────────┘

---

## 🚀 Tecnologías Utilizadas

### Backend

| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| **Java** | 17 | Lenguaje principal |
| **Spring Boot** | 4.1.0 | Framework backend |
| **Spring Web** | - | REST API |
| **Spring WebSocket** | - | Cliente WebSocket |
| **Lombok** | - | Reducción de código boilerplate |
| **Spring Actuator** | - | Health checks y métricas |
| **Swagger/OpenAPI** | 2.8.4 | Documentación API |
| **Gradle** | 9.5.1 | Gestor de dependencias |

### Frontend

| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| **Angular** | 19 | Framework frontend |
| **Angular Material** | - | UI Components |
| **RxJS** | - | Programación reactiva |
| **Signals** | - | Manejo de estado reactivo |
| **TypeScript** | - | Tipado fuerte |
| **Node.js** | 20.11.1 | Entorno de ejecución |
| **npm** | 10.2.4 | Gestor de paquetes |

---

## 📦 Estructura del Proyecto

### Backend
cti-avaya-integration/
├── src/
│ ├── main/
│ │ ├── java/com/iadv/cti/avaya/
│ │ │ ├── CtiAvayaIntegrationApplication.java
│ │ │ ├── config/
│ │ │ │ └── WebSocketConfig.java
│ │ │ ├── model/
│ │ │ │ ├── Agent.java
│ │ │ │ ├── Call.java
│ │ │ │ └── CtiEvent.java
│ │ │ ├── service/
│ │ │ │ ├── AgentStateManager.java
│ │ │ │ ├── CallStateManager.java
│ │ │ │ └── CtiWebSocketService.java
│ │ │ ├── controller/
│ │ │ │ └── CtiController.java
│ │ │ └── exception/
│ │ │ └── GlobalExceptionHandler.java
│ │ └── resources/
│ │ └── application.properties
│ └── test/
├── build.gradle
└── README.md

### Frontend
cti-frontend/
├── src/
│ ├── app/
│ │ ├── core/
│ │ │ ├── models/
│ │ │ │ ├── agent.model.ts
│ │ │ │ ├── call.model.ts
│ │ │ │ └── health.model.ts
│ │ │ └── services/
│ │ │ ├── cti-api.service.ts
│ │ │ └── cti-state.service.ts
│ │ ├── features/
│ │ │ └── dashboard/
│ │ │ ├── dashboard.component.ts
│ │ │ ├── dashboard.component.html
│ │ │ └── dashboard.component.css
│ │ ├── app.component.ts
│ │ ├── app.component.html
│ │ ├── app.component.css
│ │ └── app.config.ts
│ ├── index.html
│ └── styles.css
└── package.json

---

## 🔧 Instalación y Ejecución

### Prerrequisitos

# Verificar Java 17
java -version
# Debe mostrar: openjdk version "17.0.x"

# Verificar Node.js
node --version
# Debe mostrar: v18.x o superior

# Verificar Angular CLI
ng version
# Debe mostrar: Angular CLI: 19.x.x

1. Clonar el repositorio
git clone <url-del-repositorio>
cd CTI-AVAYA-IADV
2. Ejecutar el Backend
# Navegar a la carpeta del backend
cd cti-avaya-integration

# Construir el proyecto
./gradlew clean build

# Ejecutar la aplicación
./gradlew bootRun
El backend estará disponible en: http://localhost:8080

3. Ejecutar el Frontend
# Navegar a la carpeta del frontend
cd cti-frontend

# Instalar dependencias
npm install

# Ejecutar la aplicación
El frontend estará disponible en: http://localhost:4200

📡 Endpoints de la API
Método	Endpoint	Descripción
GET	/api/cti/health	Health check del sistema
GET	/api/cti/calls/active	Lista de llamadas activas
GET	/api/cti/agents	Estado de todos los agentes
GET	/api/cti/extensions	Estado de extensiones
POST	/api/cti/calls/{callId}/hold	Poner llamada en espera
POST	/api/cti/calls/{callId}/resume	Reanudar llamada
GET	/actuator/health	Health check de Spring Actuator
GET	/swagger-ui.html	Documentación Swagger
Ejemplo de Respuesta - /api/cti/calls/active
json
[
  {
    "callId": "CALL-12345",
    "extension": "101",
    "agentId": "A-100",
    "phoneNumber": "+1-555-123-4567",
    "status": "ANSWERED",
    "timestamp": "2026-07-14T18:55:00Z",
    "lastUpdate": "2026-07-14T18:55:00Z"
  }
]
🎨 Dashboard de Monitoreo
El dashboard muestra en tiempo real:

📊 Estadísticas
Llamadas Activas: Número total de llamadas en curso

Agentes Disponibles: Agentes listos para atender

Agentes Ocupados: Agentes en llamada

Total Agentes: Cantidad de agentes registrados

📋 Tabla de Llamadas Activas
Columna	Descripción
Call ID	Identificador único de la llamada
Extensión	Extensión telefónica asociada
Agente	ID del agente asignado
Teléfono	Número de teléfono del cliente
Estado	Estado actual de la llamada
🎨 Colores de Estados
Estado	Color	Descripción
RECEIVED	🟧 Naranja	Llamada recibida
ANSWERED	🟩 Verde	Llamada contestada
HOLD	🟧 Naranja	Llamada en espera
RESUME	🟦 Azul	Llamada reanudada
TRANSFER	🟪 Morado	Llamada transferida
ENDED	🟥 Rojo	Llamada finalizada
AVAILABLE	🟩 Verde	Agente disponible
BUSY	🟥 Rojo	Agente ocupado
⚙️ Configuración
Backend (application.properties)
properties
# Servidor
server.port=8080

# WebSocket
cti.websocket.url=ws://precook-overtone-syndrome.ngrok-free.dev
cti.websocket.reconnect-delay=5000
cti.websocket.max-retries=10

# Logging
logging.level.com.iadv.cti.avaya=DEBUG
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} - %msg%n

# Actuator
management.endpoints.web.exposure.include=health,info,metrics

# Swagger
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
Frontend (src/app/core/services/cti-api.service.ts)
typescript
private baseUrl = 'http://localhost:8080/api/cti';
🧪 Pruebas
Probar la API con cURL
bash
# Health Check
curl http://localhost:8080/api/cti/health

# Llamadas Activas
curl http://localhost:8080/api/cti/calls/active

# Agentes
curl http://localhost:8080/api/cti/agents
Probar con Postman
Importar la colección de Postman (si está disponible)

Configurar la URL base: http://localhost:8080/api/cti

Probar los endpoints

📱 Responsive Design
El dashboard es completamente responsive:

Dispositivo	Breakpoint	Comportamiento
Desktop	> 768px	Grid de 2 columnas
Tablet	480px - 768px	Grid de 1 columna, estadísticas reducidas
Mobile	< 480px	Grid de 1 columna, texto reducido
🐛 Manejo de Errores
Backend
✅ GlobalExceptionHandler para manejo centralizado de errores

✅ Logs detallados de errores con emojis y niveles

✅ Reconexión automática en caso de caída del WebSocket

Frontend
✅ Manejo de errores HTTP con catchError y retry

✅ Estados de UI para loading, error y empty

✅ Indicador de conexión Conectado/Desconectado

✅ Limpieza de suscripciones con OnDestroy

🔒 Seguridad
⚠️ Nota: Este proyecto es para fines de prueba técnica y NO incluye:

Autenticación/Autorización

Cifrado de datos

Base de datos persistente

📈 Características Implementadas
Backend
Conexión WebSocket con reconexión automática

Manejo de estado thread-safe (ConcurrentHashMap)

Eventos: CALL_RECEIVED, ANSWERED, HOLD, RESUME, TRANSFER, ENDED

APIs REST completas

Logging detallado

Manejo de eventos duplicados

Configuración externalizada

Frontend
Dashboard en tiempo real (polling 2s)

Tabla de llamadas activas

Estado de agentes

Estadísticas en vivo

Manejo de estados (loading, error, empty)

Indicador de conexión

Diseño responsive con Angular Material

Colores por estado

Acciones Hold/Resume

Extras
Swagger/OpenAPI

Spring Actuator

Retry/Backoff en reconexión

Idempotencia en eventos duplicados

Health checks avanzados

📝 Notas de Entrega
Archivos a Entregar
Código fuente completo (backend + frontend)

README.md (este documento)

Instrucciones de ejecución (incluidas en este README)

Consideraciones
✅ Proyecto compilable y ejecutable

✅ Sin dependencias externas (excepto las declaradas)

✅ Configuración lista para usar

✅ Código limpio y documentado

🤝 Contacto

Autor	IRVING ALAIN DELGADO VALVERDE
Prueba	FullStack Senior - CTI Integration
Fecha	2026

¡Gracias por revisar mi prueba técnica! 🚀
