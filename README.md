# Flight Booking Service

A Spring Boot REST API for managing flight bookings with Swagger/OpenAPI code generation and in-memory storage.

## Quick Start

### Prerequisites
- Java 17+
- Maven 3.9+

### Build & Run
```bash
cd flightBooking
mvn clean package
java -jar target/flightBooking-1.0-SNAPSHOT.jar
```

Server starts at: `http://localhost:8080`

- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI Spec: http://localhost:8080/v3/api-docs

## API Examples

### 1. Health Check
```bash
curl -X GET http://localhost:8080/api/health
```

### 2. Search Flights
```bash
curl -X POST http://localhost:8080/api/flights/search \
  -H "Content-Type: application/json" \
  -d '{"flightNumber": "AA100"}'
```

### 3. Book a Flight
```bash
curl -X POST http://localhost:8080/api/bookings \
  -H "Content-Type: application/json" \
  -d '{
    "flightNumber": "AA100",
    "numberOfPassengers": 2,
    "passengers": [
      {"firstName": "John", "lastName": "Doe", "email": "john@example.com", "phoneNumber": "+1-555-1234"},
      {"firstName": "Jane", "lastName": "Smith", "email": "jane@example.com", "phoneNumber": "+1-555-5678"}
    ]
  }'
```

### 4. View Booking
```bash
curl -X GET http://localhost:8080/api/bookings/{bookingId}
```

### 5. Cancel Booking
```bash
curl -X DELETE http://localhost:8080/api/bookings/{bookingId}
```

## Sample Flights

| Flight | Route | Seats | Price |
|--------|-------|-------|-------|
| AA100  | NYC → LAX | 150 total, 120 available | $299.99 |
| BA200  | London → NYC | 180 total, 150 available | $499.99 |
| UA300  | SFO → Chicago | 200 total, 200 available | $199.99 |

## Features

- ✅ Overbooking prevention with synchronized seat management
- ✅ OpenAPI/Swagger code generation (3 API specs)
- ✅ In-memory thread-safe storage with ConcurrentHashMap
- ✅ Global exception handling
- ✅ Clean architecture (Controllers → Services → Repositories)
- ✅ Flight search API
- ✅ Booking creation, retrieval, and cancellation
- ✅ Health check endpoint
- ✅ Swagger UI and OpenAPI documentation

## Architecture

```
src/main/java/org/ebay/flightbooking/
├── controller/          # REST endpoints
├── service/             # Business logic
├── repository/          # Data access (in-memory)
├── model/               # Internal domain models
├── exception/           # Error handling
└── Main.java            # Spring Boot app
```

## Technologies

- Spring Boot 3.1.5
- Swagger Codegen 3.0.46
- Lombok 1.18.30
- Java 17
- Maven

