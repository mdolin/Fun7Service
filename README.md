## Fun7 Service
Fun7 Service is a RESTful Spring Boot API that provides services related to checking user service statuses.

## Getting Started

These instructions will help you set up and run the project on your local machine for development and testing purposes.

### Prerequisites

Make sure you have the following tools and software installed on your machine:

- Java Development Kit (JDK)
- Apache Maven
- Integrated Development Environment (IDE) like IntelliJ IDEA (recommended) or Eclipse
- Git (optional, for cloning the project repository)

### Installing

Follow these steps to get your development environment set up:

1. Clone the repository (if you haven't already):

   ```bash
   git clone https://github.com/yourusername/fun7-service.git

2. Open the project in your preferred IDE.

3. Build the project using Maven:
   ```bash
   mvn clean install

## Running the Application
You can run the Fun7 Service application directly from your IDE or using the command line:

#### From the Command Line:
  ```bash
   mvn spring-boot:run
   ```

#### From the IDE:
1. Right-click on the Fun7ServiceApplication.java file.
2. Choose "Run Fun7ServiceApplication."

## Usage
The application provides the following endpoints:
- **GET /api/check-services**: Check user service statuses. You can access this endpoint by providing values for the timezone, userId, and cc (country code) parameters.
- **GET /admin/api/users**: List all users.
- **GET /admin/api/users/{userId}**: Get user details by ID.
- **DELETE /admin/api/users/{userId}**: Delete a user by ID.
  
### Configuration
The application uses an in-memory datastore using a simple HashMap for storing user data. This is configured in the `UserDataStore.java` class. In a production environment, consider using a persistent database like GCP Datastore or another suitable database.

### Built With
* [Spring Boot](https://spring.io/projects/spring-boot) - The framework used for building the application.
* [Maven](https://maven.apache.org/) - Dependency management and build tool.

