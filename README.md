# Delivery API

The Delivery API is a RESTful service for managing deliveries. It allows users to create, update, delete, and retrieve information about customers and deliveries, as well as register and list events associated with each delivery.

This project is using a structure following Domain Driven Design principles. It includes extensive unit tests for Domain, Controller, and Service layers, as well as custom exception handling and validation to provide a robust and secure application.

## Technologies Used

- Java 17
- Spring Boot
- Mapstruct
- Lombok
- FlywayDB with MySQL
- Swagger for API Documentation

## Testing

Extensive unit tests have been written and can be found in the `/src/test/java` directory. These tests cover the Domain, Controller and Service layers to ensure the robustness of the application.

## Error Handling

This project uses a global exception handling mechanism using `@ControllerAdvice` annotation. This approach ensures that all exceptions are caught and appropriate HTTP status codes and error messages are returned to the client.

## Validation

Request validation is implemented using JSR 380 (Bean Validation 2.0) annotations in combination with Hibernate Validator.

## API Documentation

The API documentation follows the OpenAPI 3 standard and is generated using Swagger and is available at `/swagger-ui.html` endpoint. 

## Setup

To set up the API locally, follow these steps:

1. Clone the repository.
2. Make sure you have MySQL installed and running.
3. Update the `application.properties` file with your database information.
4. Run the application using your preferred IDE or from the command line with `./mvnw spring-boot:run`.

## Endpoints

### Customer

- GET `/customer`: Get all customers.
- POST `/customer`: Add a new customer.
- GET `/customer/{customerId}`: Get a customer by ID.
- PUT `/customer/{customerId}`: Update an existing customer.
- DELETE `/customer/{customerId}`: Delete a customer by ID.

### Deliveries

- GET `/delivery`: Returns all deliveries.
- POST `/delivery`: Request a new delivery.
- GET `/delivery/{deliveryId}`: Returns a delivery by ID.
- PUT `/delivery/{deliveryId}/finish`: Finishes a delivery.

### Event

- GET `/delivery/{deliveryId}/event`: List all events for a delivery.
- POST `/delivery/{deliveryId}/event`: Register an event for a delivery.

For more details about each endpoint, including required parameters and response schemas, refer to the Swagger API documentation.

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License

[MIT](https://choosealicense.com/licenses/mit/)