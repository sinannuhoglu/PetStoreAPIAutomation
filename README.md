# PetStoreAPIAutomation

A Java-based test automation project for the Swagger PetStore REST API using RestAssured, TestNG, and Allure Reports.

---

## Project Structure

```
PetStoreAPIAutomation/
├── pom.xml                     # Maven configuration
├── testng.xml                  # TestNG suite file
├── src/
│   ├── main/
│   │   └── java/
│   │       └── org/example/base/
│   │           ├── BaseLibrary.java    # Reusable API call methods
│   │           ├── BaseTest.java       # Test base class (hooks & setup)
│   │           └── Data.java           # Global constants (IDs, baseURL, etc.)
│   └── test/
│       └── java/
│           └── org/example/
│               ├── PetTests.java       # Tests for /pet endpoints
│               ├── StoreTests.java     # Tests for /store/order endpoints
│               └── UserTests.java      # Tests for /user endpoints
```

---

## Features

- Automated test cases for:
  - Creating, updating, retrieving, and deleting pets
  - Placing and verifying orders in the store
  - Managing user lifecycle: create, update, get, delete
- Modular base classes for reusable REST operations
- Test reporting via Allure
- Parameterized and dependent tests (TestNG)

---

## Technologies Used and Their Purpose

| Technology        | Purpose                                        |
|-------------------|------------------------------------------------|
| Java              | Programming language for automation logic     |
| Maven             | Project build and dependency management       |
| RestAssured       | REST API testing library                      |
| TestNG            | Test execution and test management framework  |
| Allure            | Visual test reports and step tracking         |
| Jackson Databind  | JSON serialization/deserialization            |
| SLF4J             | Logging abstraction                           |

---

## Test Coverage

| Class         | Scenario Description                          |
|---------------|------------------------------------------------|
| PetTests      | Add, update, retrieve, and delete pets         |
| StoreTests    | Inventory check, place/delete orders           |
| UserTests     | Register, update, fetch, and delete users      |

