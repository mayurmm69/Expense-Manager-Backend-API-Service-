# Expense-Manager-Backend-API-Service-
# 💰 Expense Manager Backend API

A RESTful backend service for managing personal expenses, built using **Java**, **Spring Boot**, **Spring Security**, **Hibernate**, and **MySQL**. This project supports role-based access with JWT authentication and provides CRUD operations, pagination, search, and sorting features.

---

## 🚀 Features

- ✅ User Registration & Login with JWT Authentication
- ✅ Role-based APIs for **Admin** and **Normal User**
- ✅ Add, View, Update, Delete expenses
- ✅ View all expenses by **month**
- ✅ Expense **searching**, **pagination**, and **sorting**
- ✅ Spring Data JPA for efficient database interactions

---

## 🛠️ Tech Stack

| Technology     | Description                        |
|----------------|------------------------------------|
| Java 17        | Primary programming language       |
| Spring Boot 3  | Backend framework                  |
| Spring Security| Authentication and role management |
| JWT            | Secure token-based auth            |
| Hibernate/JPA  | ORM for database interaction       |
| MySQL          | Relational database                |
| Postman        | API testing                        |
| Maven          | Dependency & build management      |

---

## 📂 Project Structure

expense-manager-backend/
├── src/
│ ├── main/
│ │ ├── java/
│ │ │ └── com/example/expensemanager/
│ │ │ ├── controller/
│ │ │ ├── dto/
│ │ │ ├── entity/
│ │ │ ├── repository/
│ │ │ ├── security/
│ │ │ ├── service/
│ │ │ ├── exception/
│ │ │ └── ExpenseManagerApplication.java
│ │ └── resources/
│ │ ├── application.properties
│ │ └── static/
├── pom.xml
└── README.md

yaml
Copy
Edit

---

## 📦 API Endpoints

### 🔐 Auth
- `POST /api/auth/register` — Register user
- `POST /api/auth/login` — Login and receive JWT token

### 💸 Expense APIs (secured)
- `POST /api/expenses` — Add a new expense
- `GET /api/expenses` — Get all expenses (with pagination, sorting)
- `GET /api/expenses/monthly?year=2025&month=7` — View monthly expenses
- `PUT /api/expenses/{id}` — Update expense
- `DELETE /api/expenses/{id}` — Delete expense
- `GET /api/expenses/search?keyword=food` — Search expenses by keyword

---

## 🔐 Role-based Access

| Role     | Permissions                                               |
|----------|-----------------------------------------------------------|
| `USER`   | Can manage their own expenses                             |
| `ADMIN`  | Can view and manage all users’ expenses (extended APIs)   |

---

## 🧪 Run Locally

1. **Clone the repo**
```bash
git clone https://github.com/your-username/expense-manager-backend.git
cd expense-manager-backend
Configure MySQL

Create a database named expense_db and update your application.properties:
-------------------------------------------------------------------------
spring.datasource.url=jdbc:mysql://localhost:3306/expense_db
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
Run the App
-----------
mvn spring-boot:run
Test APIs using Postman
-----------------------
📝 Postman Collection
You can import the sample collection from the postman/ folder (if provided) to test registration, login, and all CRUD operations.

✍️ Author
Mayur Manish Mishra
C.V. Raman Global University
💼 Java | Spring Boot | Backend Developer

📄 License
This project is open-source and available under the MIT License.


---
