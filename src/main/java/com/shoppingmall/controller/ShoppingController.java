package com.shoppingmall.controller;

import com.shoppingmall.dao.*;
import com.shoppingmall.model.*;

import java.math.BigDecimal;
import java.util.List;

public class ShoppingController {
    private ProductDAO productDAO;
    private CustomerDAO customerDAO;
    private CartDAO cartDAO;
    private OrderDAO orderDAO;

    public ShoppingController() {
        this.productDAO = new ProductDAO();
        this.customerDAO = new CustomerDAO();
        this.cartDAO = new CartDAO();
        this.orderDAO = new OrderDAO();
    }

    // Product operations
    public List<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }

    public Product getProductById(int productId) {
        return productDAO.getProductById(productId);
    }

    public List<Product> getProductsByCategory(String category) {
        return productDAO.getProductsByCategory(category);
    }

    public boolean addProduct(Product product) {
        return productDAO.addProduct(product);
    }

    public boolean updateProduct(Product product) {
        return productDAO.updateProduct(product);
    }

    public boolean deleteProduct(int productId) {
        return productDAO.deleteProduct(productId);
    }

    // Customer operations
    public Customer authenticateCustomer(String username, String password) {
        return customerDAO.authenticate(username, password);
    }

    public boolean registerCustomer(Customer customer) {
        if (customerDAO.usernameExists(customer.getUsername())) {
            System.out.println("Username already exists!");
            return false;
        }
        return customerDAO.registerCustomer(customer);
    }

    public Customer getCustomerById(int customerId) {
        return customerDAO.getCustomerById(customerId);
    }

    public boolean updateCustomer(Customer customer) {
        return customerDAO.updateCustomer(customer);
    }

    // Cart operations
    public boolean addToCart(int customerId, int productId, int quantity) {
        Product product = productDAO.getProductById(productId);
        if (product == null) {
            System.out.println("Product not found!");
            return false;
        }
        if (product.getStockQuantity() < quantity) {
            System.out.println("Insufficient stock! Available: " + product.getStockQuantity());
            return false;
        }
        return cartDAO.addToCart(customerId, productId, quantity);
    }

    public List<CartItem> getCartItems(int customerId) {
        return cartDAO.getCartItems(customerId);
    }

    public boolean updateCartItemQuantity(int cartId, int quantity) {
        return cartDAO.updateCartItemQuantity(cartId, quantity);
    }

    public boolean removeFromCart(int cartId) {
        return cartDAO.removeFromCart(cartId);
    }

    public boolean clearCart(int customerId) {
        return cartDAO.clearCart(customerId);
    }

    public BigDecimal getCartTotal(int customerId) {
        return cartDAO.getCartTotal(customerId);
    }

    // Order operations
    public int placeOrder(int customerId) {
        List<CartItem> cartItems = cartDAO.getCartItems(customerId);
        if (cartItems.isEmpty()) {
            System.out.println("Cart is empty!");
            return -1;
        }

        // Validate stock availability
        for (CartItem item : cartItems) {
            Product product = productDAO.getProductById(item.getProductId());
            if (product.getStockQuantity() < item.getQuantity()) {
                System.out.println("Insufficient stock for: " + product.getProductName());
                return -1;
            }
        }

        BigDecimal total = cartDAO.getCartTotal(customerId);
        int orderId = orderDAO.createOrder(customerId, total, cartItems);

        if (orderId > 0) {
            // Update product stock
            for (CartItem item : cartItems) {
                productDAO.updateStock(item.getProductId(), item.getQuantity());
            }
            // Clear cart
            cartDAO.clearCart(customerId);
            return orderId;
        }
        return -1;
    }

    public List<Order> getCustomerOrders(int customerId) {
        return orderDAO.getCustomerOrders(customerId);
    }

    public Order getOrderById(int orderId) {
        return orderDAO.getOrderById(orderId);
    }

    public boolean updateOrderStatus(int orderId, String status) {
        return orderDAO.updateOrderStatus(orderId, status);
    }
}
