# Library Management System

A Spring Boot-based **Library Management System** that allows librarians to efficiently manage books by **adding, updating, searching, and deleting records** while maintaining book availability status.

---

## Features

- **Add a Book** - Store book details including `Book ID`, `Title`, `Author`, `Genre`, and `Availability Status`.
- **Search Books** - Search books **by Book ID or Title**.
- **Update Book Details** - Modify book details such as **Title, Author, or Availability Status**.
- **Delete a Book** - Remove a book from the catalog.
- **View All Books** - List all books with their details.
- **Validation & Exception Handling** - Ensures valid input and handles errors gracefully.
- **Unit Testing** - Includes **JUnit 5 & Mockito tests** for service layer validation.
- **Swagger API Documentation** - Provides an interactive UI to test API endpoints.

---

## Installation & Setup

### **1️⃣ Clone the Repository**
```sh
git clone https://github.com/Rkh2004/LibraryManagementSystem.git
cd LibraryManagementSystem
```

### **2️⃣ Set Up Database**
- Install **MySQL** and create a database:
```sql
CREATE DATABASE library_management;
```

- Update **`application.properties`** with your MySQL credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/library_management
spring.datasource.username=root
spring.datasource.password=yourpassword
```

### **3️⃣ Build & Run the Project**
- Using **Maven**:
```sh
mvn clean install
mvn spring-boot:run
```

- Using **IntelliJ IDEA / VS Code**:
  1. Open the project.
  2. Run `LibraryManagementSystemApplication.java`.

---

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| **POST** | `/api/books` | Add a new book |
| **GET** | `/api/books` | Get all books |
| **GET** | `/api/books/search?bookId=B123` | Search book by Book ID |
| **GET** | `/api/books/search?title=Java` | Search book by Title |
| **PATCH** | `/api/books/{bookId}` | Update book details |
| **DELETE** | `/api/books/{bookId}` | Delete a book |

**Base URL**: `http://localhost:8080`

### **Example: Add a Book**
#### **Request**
```http
POST http://localhost:8080/api/books
Content-Type: application/json
```
```json
{
  "bookId": "B123",
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "genre": "Programming",
  "availabilityStatus": "AVAILABLE"
}
```
#### **Response**
```json
{
  "bookId": "B123",
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "genre": "Programming",
  "availabilityStatus": "AVAILABLE"
}
```

---

## Unit Testing

### **Technologies Used for Testing:**
- **JUnit 5** - For writing test cases.
- **Mockito** - For mocking repository dependencies.
- **Spring Boot Test** - To test service layer logic.


### **Tested Methods**
| Method | Test Cases |
|--------|------------|
| `viewAllBooks()` | ✅ Returns all books |
| `addBook()` | ✅ Adds a book <br> ❌ Throws `ResourceAlreadyExistsException` for duplicate ID <br> ❌ Throws `IllegalArgumentException` for missing fields |
| `searchBooks()` | ✅ Finds book by ID/title <br> ❌ Throws `ResourceNotFoundException` if not found |
| `updateBook()` | ✅ Updates book details <br> ❌ Throws `ResourceNotFoundException` if book doesn't exist |
| `deleteBook()` | ✅ Deletes a book <br> ❌ Throws `ResourceNotFoundException` if book doesn't exist |

 **Tests ensure service layer correctness and validation.**

---

## 📊 Technologies Used

- **Java 21**
- **Spring Boot 3.x**
- **Spring Data JPA (Hibernate)**
- **MySQL**
- **Maven**
- **JUnit 5 & Mockito**
- **Swagger UI**

---

## Challenges Faced
- **Database Connectivity Issues**: Debugging connection failures with **Railway MySQL**.
- **Deployment Errors**: Resolving **JDK version mismatch** during deployment in Railway.app.
- **Environment Variables Not Loading**: Ensuring correct **MySQL credentials injection**.
- **Unit Testing Complex Scenarios**: Handling **exception cases** like `ResourceNotFoundException`.

---

## **Summary**
- **Spring Boot-based Library Management System** with CRUD operations.
- **Database: MySQL**, **Framework: Spring Boot**
- **API Endpoints** for book management.
- **Unit Testing, Exception Handling, & Deployment on Railway.**

🚀 **Ready to contribute? Fork the repo and start coding!**

