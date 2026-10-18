# Java Swing Login & Signup System

A desktop application built with **Java Swing** that lets students sign up and log in. User details are stored in a **MySQL** database through **JDBC**.

## Features

- Home window with an animated heading ("DS COLLEGE ALIGARH") that slides in on launch and changes text when you hover over it.
- **Signup form** that collects name, father's name, roll number, age and university.
- Automatically generates a **5-character username and password** (letters and digits) and shows them after a successful signup.
- **Login form** with a masked password field, checked against the users stored in MySQL.
- Database access with JDBC `PreparedStatement`s.
- Database settings are read from environment variables, so no password is stored in the code.

## Tech stack

Java · Swing / AWT · JDBC · MySQL · MySQL Connector/J

## Getting started

### Prerequisites

- JDK 8 or later
- MySQL Server
- [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/) (the JDBC driver `.jar`)

### 1. Create the database

```sql
CREATE DATABASE login_form;
USE login_form;

CREATE TABLE users (
  id       INT AUTO_INCREMENT PRIMARY KEY,
  name     VARCHAR(100),
  fname    VARCHAR(100),
  rollno   VARCHAR(30),
  age      VARCHAR(10),
  uni      VARCHAR(100),
  username VARCHAR(20),
  password VARCHAR(20)
);
```

All columns except `id` allow `NULL`, because the app also saves a row that contains only the username and password.

### 2. Set your MySQL credentials

The app reads its database settings from environment variables:

| Variable | Meaning | Default |
|---|---|---|
| `DB_PASSWORD` | Your MySQL password | **required** |
| `DB_USER` | MySQL user name | `root` |
| `DB_URL` | JDBC connection URL | `jdbc:mysql://localhost:3306/login_form` |

```bash
# Linux / macOS
export DB_PASSWORD='your-password'

# Windows (Command Prompt)
set DB_PASSWORD=your-password

# Windows (PowerShell)
$env:DB_PASSWORD='your-password'
```

Never commit real passwords to GitHub.

### 3. Compile and run

```bash
# Linux / macOS
javac -cp ".:mysql-connector-j-<version>.jar" Main.java
java  -cp ".:mysql-connector-j-<version>.jar" Main

# Windows (use ; instead of :)
javac -cp ".;mysql-connector-j-<version>.jar" Main.java
java  -cp ".;mysql-connector-j-<version>.jar" Main
```

## Project structure

```
College-login-form/
├── Main.java    # Swing UI, signup, login and database code
└── README.md
```

## Roadmap

- [x] Read database credentials from environment variables (no password in the code)
- [ ] Store hashed passwords (for example with BCrypt) instead of plain text
- [ ] Add input validation and clearer error messages
- [ ] Use layout managers instead of fixed positions
- [ ] Move database code into its own class

## Author

**Mohit Upadhyay** · [LinkedIn](https://www.linkedin.com/in/mohit-upadhyay-895529430) · [GitHub](https://github.com/Mohitblog)
