# Software Patterns Assessment 4 - ClothesStore

## Overview
This project is a Java web application for an online clothes store, developed for the Software Design Patterns module.

The application supports:
- customer registration and login
- product browsing, searching, filtering, and sorting
- shopping cart and checkout
- reviews and ratings
- order history
- admin product management
- admin customer and order management
- stock replenishment

## Technologies Used
- Java
- JSP / Servlets
- Apache Tomcat 9
- MySQL 8.4
- Eclipse IDE

## Project Structure
- `src/main/java` - Java source code
- `src/main/webapp` - JSP pages and web resources

## Setup Instructions
1. Create the MySQL database using the SQL script in `database/schema.sql`
2. Update database credentials in `DBConnectionManager`
3. Import project into Eclipse
4. Ensure MySQL Connector/J is available
5. Run the project on Tomcat 9

## Admin Test Account
- Email: `admin@clothesstore.com`
- Password: `admin123`

## Main Features
### Customer
- Register / login
- Search products by title, category, manufacturer
- Sort products
- Add to cart
- Checkout
- Leave reviews and ratings
- View order history

### Admin
- View and manage products
- View customers
- View customer orders
- Update order statuses
- Replenish stock

## Patterns Used
- Singleton
- Strategy
- State
- Observer
- Decorator
- Command
- Factory
- Null Object
- MVC architecture
