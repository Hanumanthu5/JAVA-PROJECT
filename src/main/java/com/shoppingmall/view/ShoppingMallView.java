package com.shoppingmall.view;

import com.shoppingmall.controller.ShoppingController;
import com.shoppingmall.model.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class ShoppingMallView {
    private ShoppingController controller;
    private Scanner scanner;
    private Customer currentCustomer;

    public ShoppingMallView() {
        this.controller = new ShoppingController();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║   WELCOME TO SHOPPING MALL MANAGEMENT SYSTEM    ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        while (true) {
            if (currentCustomer == null) {
                showMainMenu();
            } else {
                showCustomerMenu();
            }
        }
    }

    private void showMainMenu() {
        System.out.println("\n========== MAIN MENU ==========");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Browse Products (Guest)");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");

        int choice = getIntInput();
        switch (choice) {
            case 1:
                login();
                break;
            case 2:
                register();
                break;
            case 3:
                browseProducts();
                break;
            case 4:
                System.out.println("Thank you for visiting! Goodbye!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice! Please try again.");
        }
    }

    private void showCustomerMenu() {
        System.out.println("\n========== CUSTOMER MENU ==========");
        System.out.println("Welcome, " + currentCustomer.getFullName() + "!");
        System.out.println("1. Browse Products");
        System.out.println("2. View Cart");
        System.out.println("3. Checkout");
        System.out.println("4. View Order History");
        System.out.println("5. Update Profile");
        System.out.println("6. Logout");
        System.out.print("Enter your choice: ");

        int choice = getIntInput();
        switch (choice) {
            case 1:
                browseProductsLoggedIn();
                break;
            case 2:
                viewCart();
                break;
            case 3:
                checkout();
                break;
            case 4:
                viewOrderHistory();
                break;
            case 5:
                updateProfile();
                break;
            case 6:
                currentCustomer = null;
                System.out.println("Logged out successfully!");
                break;
            default:
                System.out.println("Invalid choice! Please try again.");
        }
    }

    private void login() {
        System.out.println("\n===== LOGIN =====");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        Customer customer = controller.authenticateCustomer(username, password);
        if (customer != null) {
            currentCustomer = customer;
            System.out.println("Login successful! Welcome, " + customer.getFullName());
        } else {
            System.out.println("Invalid username or password!");
        }
    }

    private void register() {
        System.out.println("\n===== REGISTRATION =====");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Full Name: ");
        String fullName = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Address: ");
        String address = scanner.nextLine();

        Customer customer = new Customer();
        customer.setUsername(username);
        customer.setPassword(password);
        customer.setFullName(fullName);
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setAddress(address);

        if (controller.registerCustomer(customer)) {
            System.out.println("Registration successful! You can now login.");
        } else {
            System.out.println("Registration failed! Username might already exist.");
        }
    }

    private void browseProducts() {
        System.out.println("\n===== PRODUCT CATALOG =====");
        List<Product> products = controller.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }

        for (Product product : products) {
            System.out.println(product);
        }
    }

    private void browseProductsLoggedIn() {
        System.out.println("\n===== PRODUCT CATALOG =====");
        List<Product> products = controller.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }

        for (Product product : products) {
            System.out.println(product);
        }

        System.out.print("\nAdd product to cart? (yes/no): ");
        String response = scanner.nextLine().trim().toLowerCase();
        if (response.equals("yes") || response.equals("y")) {
            addProductToCart();
        }
    }

    private void addProductToCart() {
        System.out.print("Enter Product ID: ");
        int productId = getIntInput();
        System.out.print("Enter Quantity: ");
        int quantity = getIntInput();

        if (controller.addToCart(currentCustomer.getCustomerId(), productId, quantity)) {
            System.out.println("Product added to cart successfully!");
        } else {
            System.out.println("Failed to add product to cart!");
        }
    }

    private void viewCart() {
        System.out.println("\n===== YOUR SHOPPING CART =====");
        List<CartItem> cartItems = controller.getCartItems(currentCustomer.getCustomerId());
        
        if (cartItems.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }

        for (CartItem item : cartItems) {
            System.out.println(item);
        }

        BigDecimal total = controller.getCartTotal(currentCustomer.getCustomerId());
        System.out.println("─────────────────────────────────────────────────────────────────");
        System.out.printf("TOTAL: $%.2f%n", total);

        System.out.println("\n1. Remove item from cart");
        System.out.println("2. Update item quantity");
        System.out.println("3. Clear cart");
        System.out.println("4. Back to menu");
        System.out.print("Enter your choice: ");

        int choice = getIntInput();
        switch (choice) {
            case 1:
                System.out.print("Enter Cart ID to remove: ");
                int cartId = getIntInput();
                if (controller.removeFromCart(cartId)) {
                    System.out.println("Item removed successfully!");
                }
                break;
            case 2:
                System.out.print("Enter Cart ID: ");
                cartId = getIntInput();
                System.out.print("Enter new quantity: ");
                int quantity = getIntInput();
                if (controller.updateCartItemQuantity(cartId, quantity)) {
                    System.out.println("Quantity updated successfully!");
                }
                break;
            case 3:
                if (controller.clearCart(currentCustomer.getCustomerId())) {
                    System.out.println("Cart cleared successfully!");
                }
                break;
        }
    }

    private void checkout() {
        System.out.println("\n===== CHECKOUT =====");
        List<CartItem> cartItems = controller.getCartItems(currentCustomer.getCustomerId());
        
        if (cartItems.isEmpty()) {
            System.out.println("Your cart is empty. Add products before checkout.");
            return;
        }

        System.out.println("Cart Summary:");
        for (CartItem item : cartItems) {
            System.out.println(item);
        }

        BigDecimal total = controller.getCartTotal(currentCustomer.getCustomerId());
        System.out.println("─────────────────────────────────────────────────────────────────");
        System.out.printf("TOTAL: $%.2f%n", total);

        System.out.print("\nConfirm order? (yes/no): ");
        String response = scanner.nextLine().trim().toLowerCase();
        
        if (response.equals("yes") || response.equals("y")) {
            int orderId = controller.placeOrder(currentCustomer.getCustomerId());
            if (orderId > 0) {
                System.out.println("Order placed successfully! Order ID: " + orderId);
                System.out.println("Thank you for your purchase!");
            } else {
                System.out.println("Failed to place order. Please try again.");
            }
        } else {
            System.out.println("Order cancelled.");
        }
    }

    private void viewOrderHistory() {
        System.out.println("\n===== ORDER HISTORY =====");
        List<Order> orders = controller.getCustomerOrders(currentCustomer.getCustomerId());
        
        if (orders.isEmpty()) {
            System.out.println("No orders found.");
            return;
        }

        for (Order order : orders) {
            System.out.println(order);
        }
    }

    private void updateProfile() {
        System.out.println("\n===== UPDATE PROFILE =====");
        System.out.print("Full Name [" + currentCustomer.getFullName() + "]: ");
        String fullName = scanner.nextLine();
        System.out.print("Email [" + currentCustomer.getEmail() + "]: ");
        String email = scanner.nextLine();
        System.out.print("Phone [" + currentCustomer.getPhone() + "]: ");
        String phone = scanner.nextLine();
        System.out.print("Address [" + currentCustomer.getAddress() + "]: ");
        String address = scanner.nextLine();

        if (!fullName.isEmpty()) currentCustomer.setFullName(fullName);
        if (!email.isEmpty()) currentCustomer.setEmail(email);
        if (!phone.isEmpty()) currentCustomer.setPhone(phone);
        if (!address.isEmpty()) currentCustomer.setAddress(address);

        if (controller.updateCustomer(currentCustomer)) {
            System.out.println("Profile updated successfully!");
        } else {
            System.out.println("Failed to update profile.");
        }
    }

    private int getIntInput() {
        while (true) {
            try {
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
}
