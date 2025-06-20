# SplitApp - Expense Splitter Backend

A Spring Boot REST API application for splitting expenses among multiple participants with flexible splitting options.

## 🚀 Features

- **Multiple Split Types**: Equal, Percentage, and Exact amount splitting
- **Expense Management**: Create, read, update, and delete expenses
- **Settlement Calculations**: Automatic calculation of who owes whom
- **MongoDB Integration**: Persistent data storage
- **Input Validation**: Comprehensive validation for all inputs
- **RESTful API**: Clean and intuitive API endpoints
- **Docker Support**: Containerized deployment ready

## 🛠️ Technology Stack

- **Java 17**
- **Spring Boot 3.2.5**
- **Spring Data MongoDB**
- **Spring Boot Validation**
- **Lombok** (for reducing boilerplate code)
- **Maven** (build tool)
- **Docker** (containerization)

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- MongoDB (local installation or MongoDB Atlas)
- Docker (optional, for containerized deployment)

## 🏗️ Project Structure

```
splitapp/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── splitapp/
│                   ├── controller/          # REST Controllers
│                   │   ├── ExpenseController.java
│                   │   └── SettlementController.java
│                   ├── model/               # Data Models
│                   │   └── Expense.java
│                   ├── repository/          # Data Access Layer
│                   ├── service/             # Business Logic
│                   └── SplitAppApplication.java
├── target/                                  # Build output
├── Dockerfile                               # Docker configuration
├── pom.xml                                  # Maven configuration
└── README.md
```

## 🚀 Getting Started

### Local Development

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd splitapp
   ```

2. **Configure MongoDB**
   - Install MongoDB locally or use MongoDB Atlas
   - Update `application.properties` with your MongoDB connection string

3. **Build the application**
   ```bash
   mvn clean compile
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

The application will start on `http://localhost:8080`

### Docker Deployment

1. **Build the JAR file**
   ```bash
   mvn clean package
   ```

2. **Build Docker image**
   ```bash
   docker build -t splitapp .
   ```

3. **Run the container**
   ```bash
   docker run -p 8080:8080 splitapp
   ```

## 📚 API Documentation

### Base URL
```
http://localhost:8080
```

### Expense Endpoints

#### Get All Expenses
```http
GET /expenses
```

**Response:**
```json
{
  "success": true,
  "data": [
    {
      "id": "expense_id",
      "amount": 100.0,
      "description": "Dinner",
      "paidBy": "John",
      "participants": ["John", "Jane", "Bob"],
      "splitType": "EQUAL",
      "splitDetails": {}
    }
  ],
  "message": "Expenses fetched successfully"
}
```

#### Add New Expense
```http
POST /expenses
Content-Type: application/json
```

**Request Body:**
```json
{
  "amount": 150.0,
  "description": "Restaurant Bill",
  "paidBy": "Alice",
  "participants": ["Alice", "Bob", "Charlie"],
  "splitType": "EQUAL"
}
```

#### Update Expense
```http
PUT /expenses/{id}
Content-Type: application/json
```

#### Delete Expense
```http
DELETE /expenses/{id}
```

### Split Types

1. **EQUAL**: Split amount equally among all participants
2. **PERCENTAGE**: Split based on percentage (splitDetails required)
3. **EXACT**: Split based on exact amounts (splitDetails required)

**Example with splitDetails:**
```json
{
  "amount": 100.0,
  "description": "Groceries",
  "paidBy": "John",
  "participants": ["John", "Jane", "Bob"],
  "splitType": "PERCENTAGE",
  "splitDetails": {
    "John": 50.0,
    "Jane": 30.0,
    "Bob": 20.0
  }
}
```

## 🧪 Testing

Run tests using Maven:
```bash
mvn test
```

## 📝 Data Model

### Expense
- `id`: Unique identifier
- `amount`: Total expense amount (must be positive)
- `description`: Description of the expense (required)
- `paidBy`: Person who paid the expense (required)
- `participants`: List of people involved in the expense (required)
- `splitType`: How to split the expense (EQUAL, PERCENTAGE, EXACT)
- `splitDetails`: Additional split information for PERCENTAGE/EXACT splits

## 🔧 Configuration

### Application Properties
Create `src/main/resources/application.properties`:

```properties
# MongoDB Configuration
spring.data.mongodb.uri=mongodb://localhost:27017/splitapp

# Server Configuration
server.port=8080

# Logging
logging.level.com.splitapp=DEBUG
```

## 🐳 Docker Configuration

The application includes a Dockerfile for easy containerization:
- Based on OpenJDK 17 slim image
- Exposes port 8080
- Runs the Spring Boot JAR file

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🔮 Future Enhancements

- [ ] User authentication and authorization
- [ ] Group management functionality
- [ ] Email notifications for settlements
- [ ] Mobile app integration
- [ ] Advanced reporting and analytics
- [ ] Multi-currency support
- [ ] Recurring expense management

## 📞 Support

For support and questions:
- Create an issue in the repository
- Contact the development team

---

**Happy Splitting! 💰**
