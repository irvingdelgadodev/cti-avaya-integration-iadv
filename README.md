# CTI Integration - FullStack Technical Test

## ?? Informaci¨®n del Proyecto

| Campo | Valor |
|-------|-------|
| **Proyecto** | CTI Integration con AVAYA |
| **Autor** | Irving Delgado |
| **Fecha** | 2026 |
| **Versi¨®n** | 1.0.0 |
| **Repositorio** | https://github.com/irvingdelgadodev/cti-avaya-integration-iadv |

---

## ?? Descripci¨®n

Prueba t¨¦cnica para **Desarrollador FullStack Senior** que simula una integraci¨®n con una plataforma telef¨®nica **AVAYA AES** utilizando un Mock CTI Server basado en WebSockets.

### ?? Objetivo

- Integraci¨®n CTI con AVAYA
- Procesamiento de eventos en tiempo real
- Manejo de WebSockets
- Arquitectura backend robusta
- Dashboard Angular en tiempo real

---

## ??? Tecnolog¨ªas Utilizadas

### Backend

| Tecnolog¨ªa | Versi¨®n | Prop¨®sito |
|------------|---------|-----------|
| Java | 17 | Lenguaje principal |
| Spring Boot | 4.1.0 | Framework backend |
| Spring Web | - | REST API |
| Spring WebSocket | - | Cliente WebSocket |
| Lombok | - | Reducci¨®n de c¨®digo |
| Spring Actuator | - | Health checks |
| Swagger/OpenAPI | 2.8.4 | Documentaci¨®n API |
| Gradle | 9.5.1 | Gestor de dependencias |

### Frontend

| Tecnolog¨ªa | Versi¨®n | Prop¨®sito |
|------------|---------|-----------|
| Angular | 19 | Framework frontend |
| Angular Material | - | UI Components |
| RxJS | - | Programaci¨®n reactiva |
| Signals | - | Manejo de estado |
| TypeScript | - | Tipado fuerte |
| Node.js | 20.11.1 | Entorno de ejecuci¨®n |

---

## ?? Estructura del Proyecto
cti-avaya-integration-iadv/
©À©¤©¤ cti-avaya-integration/ # Backend (Spring Boot)
©¦ ©À©¤©¤ src/
©¦ ©¦ ©¸©¤©¤ main/
©¦ ©¦ ©À©¤©¤ java/com/iadv/cti/avaya/
©¦ ©¦ ©¦ ©À©¤©¤ CtiAvayaIntegrationApplication.java
©¦ ©¦ ©¦ ©À©¤©¤ config/WebSocketConfig.java
©¦ ©¦ ©¦ ©À©¤©¤ model/ (Call, Agent, CtiEvent)
©¦ ©¦ ©¦ ©À©¤©¤ service/ (CallStateManager, AgentStateManager, CtiWebSocketService)
©¦ ©¦ ©¦ ©À©¤©¤ controller/CtiController.java
©¦ ©¦ ©¦ ©¸©¤©¤ exception/GlobalExceptionHandler.java
©¦ ©¦ ©¸©¤©¤ resources/application.properties
©¦ ©À©¤©¤ build.gradle
©¦ ©¸©¤©¤ README.md
©¦
©À©¤©¤ cti-frontend/ # Frontend (Angular)
©¦ ©À©¤©¤ src/
©¦ ©¦ ©¸©¤©¤ app/
©¦ ©¦ ©À©¤©¤ core/
©¦ ©¦ ©¦ ©À©¤©¤ models/ (call.model.ts, agent.model.ts, health.model.ts)
©¦ ©¦ ©¦ ©¸©¤©¤ services/ (cti-api.service.ts, cti-state.service.ts)
©¦ ©¦ ©À©¤©¤ features/dashboard/ (dashboard.component.ts|html|css)
©¦ ©¦ ©À©¤©¤ app.component.ts|html|css
©¦ ©¦ ©¸©¤©¤ app.config.ts
©¦ ©À©¤©¤ package.json
©¦ ©¸©¤©¤ README.md
©¦
©À©¤©¤ .gitignore
©¸©¤©¤ README.md

---

## ?? Instalaci¨®n y Ejecuci¨®n

### Prerrequisitos

```bash
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

git clone https://github.com/irvingdelgadodev/cti-avaya-integration-iadv.git
cd cti-avaya-integration-iadv
2. Ejecutar el Backend

# Navegar a la carpeta del backend
cd cti-avaya-integration

# Construir el proyecto
./gradlew clean build

# Ejecutar la aplicaci¨®n
./gradlew bootRun
El backend estar¨¢ disponible en: http://localhost:8080

3. Ejecutar el Frontend

# Navegar a la carpeta del frontend
cd cti-frontend

# Instalar dependencias
npm install

# Ejecutar la aplicaci¨®n
ng serve
El frontend estar¨¢ disponible en: http://localhost:4200

?? Endpoints de la API
M¨¦todo	Endpoint	Descripci¨®n
GET	/api/cti/health	Health check del sistema
GET	/api/cti/calls/active	Lista de llamadas activas
GET	/api/cti/agents	Estado de todos los agentes
GET	/api/cti/extensions	Estado de extensiones
POST	/api/cti/calls/{callId}/hold	Poner llamada en espera
POST	/api/cti/calls/{callId}/resume	Reanudar llamada
GET	/actuator/health	Health check de Spring Actuator
GET	/swagger-ui.html	Documentaci¨®n Swagger
Ejemplo de Respuesta
json
{
  "callId": "CALL-12345",
  "extension": "101",
  "agentId": "A-100",
  "phoneNumber": "+1-555-123-4567",
  "status": "ANSWERED",
  "timestamp": "2026-07-14T18:55:00Z",
  "lastUpdate": "2026-07-14T18:55:00Z"
}
?? Dashboard de Monitoreo
El dashboard muestra en tiempo real:

?? Estad¨ªsticas
Llamadas Activas

Agentes Disponibles

Agentes Ocupados

Total Agentes

?? Tabla de Llamadas Activas
Columna	Descripci¨®n
Call ID	Identificador ¨²nico de la llamada
Extensi¨®n	Extensi¨®n telef¨®nica asociada
Agente	ID del agente asignado
Tel¨¦fono	N¨²mero de tel¨¦fono del cliente
Estado	Estado actual de la llamada
?? Colores de Estados
Estado	Color	Descripci¨®n
RECEIVED	?? Naranja	Llamada recibida
ANSWERED	?? Verde	Llamada contestada
HOLD	?? Naranja	Llamada en espera
RESUME	?? Azul	Llamada reanudada
TRANSFER	?? Morado	Llamada transferida
ENDED	?? Rojo	Llamada finalizada
AVAILABLE	?? Verde	Agente disponible
BUSY	?? Rojo	Agente ocupado
?? Configuraci¨®n
Backend (application.properties)
properties
server.port=8080

cti.websocket.url=ws://precook-overtone-syndrome.ngrok-free.dev
cti.websocket.reconnect-delay=5000
cti.websocket.max-retries=10

logging.level.com.iadv.cti.avaya=DEBUG
management.endpoints.web.exposure.include=health,info,metrics
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
Frontend (src/app/core/services/cti-api.service.ts)
typescript
private baseUrl = 'http://localhost:8080/api/cti';
?? Pruebas R¨¢pidas
Probar la API con cURL
bash
# Health Check
curl http://localhost:8080/api/cti/health

# Llamadas Activas
curl http://localhost:8080/api/cti/calls/active

# Agentes
curl http://localhost:8080/api/cti/agents
?? Responsive Design
El dashboard es completamente responsive:

Dispositivo	Breakpoint	Comportamiento
Desktop	> 768px	Grid de 2 columnas
Tablet	480px - 768px	Grid de 1 columna
Mobile	< 480px	Grid de 1 columna, texto reducido
?? Manejo de Errores
Backend
GlobalExceptionHandler para manejo centralizado de errores

Logs detallados de errores con emojis y niveles

Reconexi¨®n autom¨¢tica en caso de ca¨ªda del WebSocket

Frontend
Manejo de errores HTTP con catchError y retry

Estados de UI para loading, error y empty

Indicador de conexi¨®n Conectado/Desconectado

Limpieza de suscripciones con OnDestroy

?? Caracter¨ªsticas Implementadas
Backend
Conexi¨®n WebSocket con reconexi¨®n autom¨¢tica

Manejo de estado thread-safe (ConcurrentHashMap)

Eventos: CALL_RECEIVED, ANSWERED, HOLD, RESUME, TRANSFER, ENDED

APIs REST completas

Logging detallado

Manejo de eventos duplicados

Configuraci¨®n externalizada

Frontend
Dashboard en tiempo real (polling 2s)

Tabla de llamadas activas

Estado de agentes

Estad¨ªsticas en vivo

Manejo de estados (loading, error, empty)

Indicador de conexi¨®n

Dise?o responsive con Angular Material

Colores por estado

Acciones Hold/Resume

Extras
Swagger/OpenAPI

Spring Actuator

Retry/Backoff en reconexi¨®n

Idempotencia en eventos duplicados

Health checks avanzados

?? Seguridad
?? Nota: Este proyecto es para fines de prueba t¨¦cnica y NO incluye:

Autenticaci¨®n/Autorizaci¨®n

Cifrado de datos

Base de datos persistente

?? Notas de Entrega
Archivos Entregados
C¨®digo fuente completo (backend + frontend)

README.md con instrucciones

Proyecto compilable y ejecutable

Consideraciones
? Proyecto compilable y ejecutable

? Sin dependencias externas (excepto las declaradas)

? Configuraci¨®n lista para usar

? C¨®digo limpio y documentado

?? Autor
Campo	Valor
Nombre	Irving Delgado
Prueba	FullStack Senior - CTI Integration
Fecha	2026

?Gracias por revisar mi prueba t¨¦cnica! ??