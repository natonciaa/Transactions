Transactions API 

API REST para la gestión de transacciones con funcionalidades de:
- Crear, editar, eliminar y listar transacciones.
- Filtrar por nombre, fecha y estado.
- Realizar pagos.
- Restricción de rate limit, una transacción por minuto
- Persistencia en base de datos H2
- Implementado con Spring Boot + JPA + Clean Architecture

 Tecnologías
- Java 17
- Spring Boot 3
- Spring Data JPA
- H2 Database
- Lombok
- JUnit + Mockito

  ENDPOINTS
  - Crear transacción
POST : http://localhost:8080/transactions
Request: JSON
{"nombre": "Lunch","valor": 10000}

Response: JSON
{"id":3,"nombre":"Lunch","fecha":[2025,8,25,18,19,26,829242600],"valor":10000,"estado":"PENDIENTE"}

- Editar transacción
PUT: http://localhost:8080/transactions/id
Request: JSON {"nombre": "Breakfast","valor": 20000}
Response: JSON {"id":1,"nombre":"Breakfast","fecha":[2025,8,25,18,13,28,375288000],"valor":20000,"estado":"PENDIENTE"}

- Listar transacciones
GET: http://localhost:8080/transactions
Response: [{"id":1,"nombre":"Compra 1","fecha":"2025-08-26 11:01:52","valor":10000.00,"estado":"PENDIENTE"}]

- Eliminar
DELETE: http://localhost:8080/transactions/id

Response : status 204 no content 
          sstatus 404 No existe la transaccion: id
