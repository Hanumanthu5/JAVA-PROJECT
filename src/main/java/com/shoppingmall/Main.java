package com.shoppingmall;

import com.shoppingmall.util.DatabaseConnection;
import com.shoppingmall.view.ShoppingMallView;

public class Main {
    public static void main(String[] args) {
        System.out.println("Initializing Shopping Mall Management System...");
        
        // Initialize database
        DatabaseConnection.initializeDatabase();
        
        // Start the application
        ShoppingMallView view = new ShoppingMallView();
        view.start();
    }
}
