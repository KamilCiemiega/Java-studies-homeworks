# TicketHub

A Spring Boot application for managing ticket reservations and bookings for events. TicketHub provides a complete event management system with ticket reservations, shopping cart functionality, and admin capabilities.

## Table of Contents

- [Features](#features)
- [Technology Stack](#technology-stack)
- [Prerequisites](#prerequisites)
- [How to Run](#how-to-run)
- [Project Structure](#project-structure)
- [API Endpoints](#api-endpoints)
- [Usage Examples](#usage-examples)

## Features

### User Features
- **Browse Events**: View all available events with details (title, description, date, location, ticket price)
- **Event Details**: Click on an event to see full details and availability
- **Shopping Cart**: Add events to cart and manage items before checkout
- **Reservations**: Create reservations for events with personal information (first name, last name, email)
- **View Event Reservations**: See all reservations made for each event

### Admin Features
- **Admin Login**: Secure login system (credentials: username: `admin`, password: `admin123`)
- **Create Events**: Add new events to the system with all required details
- **Edit Events**: Modify existing event information
- **Delete Events**: Remove events from the system
- **Inventory Management**: Track available tickets for each event
- **View Reservations**: Access all customer reservations

### Technical Features
- **Asynchronous Processing**: Uses async processing for ticket processing operations
- **H2 Database**: In-memory database with file persistence
- **Data initialization**: Sample events are automatically loaded on startup
- **Responsive UI**: Thymeleaf-based templates for web interface

## Technology Stack

- **Language**: Java 21
- **Framework**: Spring Boot 3.5.15
- **Template Engine**: Thymeleaf
- **Database**: H2 (with file-based persistence)
- **ORM**: Spring Data JPA with Hibernate
- **Build Tool**: Maven
- **Additional Libraries**: 
  - Lombok (for reducing boilerplate code)
  - Spring Validation
  - Spring DevTools (for development)

## Prerequisites

- **Java 21** or higher installed
- **Maven 3.6+** installed (or use Maven wrapper included in the project)
- **Git** (optional, for cloning the repository)

## How to Run

### Option 1: Using Maven Wrapper (Windows)

1. Open PowerShell or Command Prompt in the project root directory
2. Run the following command:

```powershell
.\mvnw.cmd spring-boot:run
```

### Option 2: Using Maven

If you have Maven installed globally:

```bash
mvn clean install
mvn spring-boot:run
```

### Option 3: Using IDE (IntelliJ IDEA, Eclipse, etc.)

1. Open the project in your IDE
2. Maven dependencies will be downloaded automatically
3. Navigate to `TicketHubApplication.java` in `src/main/java/tickethub`
4. Right-click on the file and select "Run" or "Debug"

### Option 4: Building and Running JAR

```bash
mvn clean package
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

### After Starting the Application

1. Open your web browser
2. Navigate to `http://localhost:8080`
3. The home page will display all available events

### Accessing H2 Console (for database inspection)

- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:file:./data/tickethub`
- Username: `sa`
- Password: (leave empty)

## Project Structure

```
TicketHub/
├── src/
│   ├── main/
│   │   ├── java/tickethub/
│   │   │   ├── TicketHubApplication.java          # Main Spring Boot application class
│   │   │   ├── cart/
│   │   │   │   ├── Cart.java                      # Shopping cart model
│   │   │   │   └── CartItem.java                  # Individual cart item
│   │   │   ├── config/
│   │   │   │   └── DataInitializer.java           # Sample data initialization
│   │   │   ├── controller/
│   │   │   │   ├── EventController.java           # Web UI for events
│   │   │   │   ├── CartController.java            # Shopping cart operations
│   │   │   │   ├── LoginController.java           # Authentication
│   │   │   │   ├── EventRestController.java       # REST API for events
│   │   │   │   └── ReservationRestController.java # REST API for reservations
│   │   │   ├── entity/
│   │   │   │   ├── Event.java                     # Event entity
│   │   │   │   └── Reservation.java               # Reservation entity
│   │   │   ├── exception/
│   │   │   │   └── GlobalExceptionHandler.java    # Global exception handling
│   │   │   ├── repository/
│   │   │   │   ├── EventRepository.java           # Event data access
│   │   │   │   └── ReservationRepository.java     # Reservation data access
│   │   │   └── service/
│   │   │       ├── EventService.java              # Event business logic
│   │   │       ├── CartService.java               # Cart business logic
│   │   │       ├── ReservationService.java        # Reservation business logic
│   │   │       └── TicketAsyncService.java        # Async ticket processing
│   │   └── resources/
│   │       ├── application.yaml                   # Application configuration
│   │       ├── static/                            # Static files (CSS, JS, images)
│   │       └── templates/                         # Thymeleaf HTML templates
│   │           ├── events.html                    # Events list page
│   │           ├── event-details.html             # Event details page
│   │           ├── event-form.html                # Event creation form (admin)
│   │           ├── cart.html                      # Shopping cart page
│   │           └── login.html                     # Login page
│   └── test/
│       ├── java/tickethub/
│       │   └── TicketHubApplicationTests.java     # Integration tests
├── pom.xml                                        # Maven configuration
├── mvnw & mvnw.cmd                               # Maven wrapper scripts
└── README.md                                      # This file
```

## API Endpoints

### Web UI Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/` | View all events |
| GET | `/event/{id}` | View event details |
| POST | `/reserve/{id}` | Create a reservation for an event |
| GET | `/events/new` | Show event creation form (admin only) |
| POST | `/events` | Save a new event (admin only) |
| POST | `/event/delete/{id}` | Delete an event (admin only) |
| GET | `/login` | Show login page |
| POST | `/login` | Process login |
| GET | `/logout` | Logout and invalidate session |
| POST | `/cart/add/{eventId}` | Add event to shopping cart |
| GET | `/cart` | View shopping cart |
| POST | `/cart/checkout` | Process cart checkout |

### REST API Endpoints

- `/api/events` - Event REST endpoints
- `/api/reservations` - Reservation REST endpoints

## Usage Examples

### 1. Viewing Events
- Navigate to `http://localhost:8080`
- All available events will be displayed on the home page

### 2. Making a Reservation
- Click on an event to view details
- Enter your first name, last name, and email
- Click "Reserve" to complete the reservation

### 3. Using Shopping Cart
- From event details page, click "Add to Cart"
- Navigate to `/cart` to view your cart
- Click "Checkout" to process all items in the cart

### 4. Admin Operations
- Navigate to `http://localhost:8080/login`
- Login with:
  - Username: `admin`
  - Password: `admin123`
- After login, admin options will appear:
  - "Create New Event" button
  - Edit and delete options on each event

### 5. Creating a New Event (Admin)
- Login as admin
- Click "Create New Event"
- Fill in event details:
  - Title
  - Description
  - Event Date & Time
  - Location
  - Ticket Price
  - Available Tickets
- Click "Create" to save

## Database Initialization

The application automatically initializes sample events on startup using the `DataInitializer` class. This ensures there's test data available when the application starts.

## Configuration

Key configuration settings in `application.yaml`:

- **Server Port**: 8080
- **Database**: H2 file-based (data stored in `./data/tickethub`)
- **JPA**: Hibernate with automatic DDL updates
- **Thymeleaf**: Caching disabled for development
- **H2 Console**: Enabled at `/h2-console` for database inspection

## Notes

- The application uses session-based authentication for admin access
- The shopping cart is stored in the HTTP session
- Tickets are managed with optimistic locking (version column)
- Async processing is enabled for ticket operations
- Data is persisted to disk in the `./data/tickethub` directory

## Future Enhancements

Potential features for future development:
- User registration and account management
- Email notifications for reservations
- Payment gateway integration
- Multi-language support
- Advanced search and filtering
- Mobile app
- API rate limiting and security enhancements
- More sophisticated authentication (OAuth2, JWT)

## License

This project is for educational purposes as part of Java studies and homework assignments.

