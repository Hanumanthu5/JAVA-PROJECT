# Shopping Mall Management System

A comprehensive Java-based Shopping Mall Management System built using **MVC (Model-View-Controller)** architecture pattern with **JDBC** for database operations and **SQL** for data persistence.

## Features

### Customer Features
- **User Registration & Authentication**: Customers can register and login securely
- **Product Browsing**: View all available products with details (name, price, stock, category)
- **Shopping Cart**: Add, update, and remove products from cart
- **Order Management**: Place orders and view order history
- **Profile Management**: Update customer profile information

### Product Management
- Browse products by category
- View detailed product information
- Real-time stock management
- Product categories: Electronics, Clothing, Footwear, Home Appliances, Accessories

### System Features
- SQLite database (no installation required - perfect for development)
- MySQL support (configurable)
- Transaction management for order processing
- Stock validation before order placement
- Automatic database schema initialization

## Architecture

The application follows the **MVC (Model-View-Controller)** design pattern:

### Model Layer (`com.shoppingmall.model`)
- **Product.java**: Represents product entities
- **Customer.java**: Represents customer entities
- **Order.java**: Represents order entities
- **CartItem.java**: Represents shopping cart items

### DAO Layer (`com.shoppingmall.dao`)
Data Access Objects for database operations using JDBC:
- **ProductDAO.java**: CRUD operations for products
- **CustomerDAO.java**: Customer authentication and management
- **CartDAO.java**: Shopping cart operations
- **OrderDAO.java**: Order processing and history

### Controller Layer (`com.shoppingmall.controller`)
- **ShoppingController.java**: Business logic and coordination between Model and View

### View Layer (`com.shoppingmall.view`)
- **ShoppingMallView.java**: Console-based user interface

### Utility Layer (`com.shoppingmall.util`)
- **DatabaseConnection.java**: Database connection management and initialization

## Database Schema

The application uses the following tables:
- **products**: Store product information
- **customers**: Store customer accounts
- **orders**: Store order information
- **order_items**: Store individual items in each order
- **cart**: Temporary shopping cart storage

## Prerequisites

- Java Development Kit (JDK) 11 or higher
- Maven 3.6 or higher

## Installation & Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/Hanumanthu5/JAVA-PROJECT.git
   cd JAVA-PROJECT
   ```

2. **Build the project**
   ```bash
   mvn clean compile
   ```

3. **Run the application**
   ```bash
   mvn exec:java -Dexec.mainClass="com.shoppingmall.Main"
   ```

## Configuration

### Database Configuration

The application uses SQLite by default (no setup required). To use MySQL instead:

1. Edit `src/main/resources/db.properties`
2. Comment out SQLite configuration
3. Uncomment MySQL configuration and update credentials:
   ```properties
   db.url=jdbc:mysql://localhost:3306/shoppingmall?useSSL=false&serverTimezone=UTC
   db.username=your_username
   db.password=your_password
   db.driver=com.mysql.cj.jdbc.Driver
   ```

4. Create MySQL database:
   ```sql
   CREATE DATABASE shoppingmall;
   ```

## Usage

### Main Menu
1. **Login**: Access your customer account
2. **Register**: Create a new customer account
3. **Browse Products (Guest)**: View products without logging in
4. **Exit**: Close the application

### Customer Menu (After Login)
1. **Browse Products**: View all products and add to cart
2. **View Cart**: Manage your shopping cart
3. **Checkout**: Complete your purchase
4. **View Order History**: See your past orders
5. **Update Profile**: Update your account information
6. **Logout**: Sign out from your account

## Sample Products

The database comes pre-populated with 10 sample products:
- Laptop ($899.99)
- Smartphone ($699.99)
- T-Shirt ($19.99)
- Jeans ($49.99)
- Running Shoes ($79.99)
- Coffee Maker ($129.99)
- Backpack ($39.99)
- Headphones ($199.99)
- Watch ($149.99)
- Microwave ($89.99)

## Project Structure

```
JAVA-PROJECT/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── shoppingmall/
│       │           ├── Main.java
│       │           ├── model/
│       │           │   ├── Product.java
│       │           │   ├── Customer.java
│       │           │   ├── Order.java
│       │           │   └── CartItem.java
│       │           ├── dao/
│       │           │   ├── ProductDAO.java
│       │           │   ├── CustomerDAO.java
│       │           │   ├── CartDAO.java
│       │           │   └── OrderDAO.java
│       │           ├── controller/
│       │           │   └── ShoppingController.java
│       │           ├── view/
│       │           │   └── ShoppingMallView.java
│       │           └── util/
│       │               └── DatabaseConnection.java
│       └── resources/
│           ├── db.properties
│           └── schema.sql
├── pom.xml
└── README.md
```

## Technologies Used

- **Java 11**: Core programming language
- **Maven**: Build and dependency management
- **JDBC**: Database connectivity
- **SQLite**: Default embedded database
- **MySQL**: Optional database (Connector/J 8.0.33)
- **MVC Pattern**: Application architecture

## Key Features of Implementation

### MVC Architecture
- **Separation of Concerns**: Clear separation between data (Model), business logic (Controller), and presentation (View)
- **Maintainability**: Easy to modify and extend each layer independently
- **Testability**: Each component can be tested in isolation

### JDBC Implementation
- **PreparedStatement**: Protection against SQL injection
- **Connection Pooling**: Efficient database connection management
- **Transaction Management**: ACID compliance for order processing
- **Resource Management**: Proper closing of database resources

### SQL Database
- **Normalized Schema**: Proper table relationships with foreign keys
- **Data Integrity**: Constraints and validations
- **Sample Data**: Pre-populated products for testing

## Future Enhancements

- Admin panel for product management
- Payment gateway integration
- Order tracking system
- Product reviews and ratings
- Discount and coupon system
- Email notifications
- Advanced search and filters
- Web-based UI using Spring Boot

## License

This project is open source and available for educational purposes.

## Author

Hanumanthu5

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.