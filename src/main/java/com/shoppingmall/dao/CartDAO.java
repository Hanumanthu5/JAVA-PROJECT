package com.shoppingmall.dao;

import com.shoppingmall.model.CartItem;
import com.shoppingmall.util.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {

    public boolean addToCart(int customerId, int productId, int quantity) {
        String checkSql = "SELECT cart_id, quantity FROM cart WHERE customer_id = ? AND product_id = ?";
        String insertSql = "INSERT INTO cart (customer_id, product_id, quantity) VALUES (?, ?, ?)";
        String updateSql = "UPDATE cart SET quantity = quantity + ? WHERE cart_id = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            // Check if item already exists in cart
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setInt(1, customerId);
                checkStmt.setInt(2, productId);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    // Update existing cart item
                    int cartId = rs.getInt("cart_id");
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setInt(1, quantity);
                        updateStmt.setInt(2, cartId);
                        return updateStmt.executeUpdate() > 0;
                    }
                } else {
                    // Insert new cart item
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                        insertStmt.setInt(1, customerId);
                        insertStmt.setInt(2, productId);
                        insertStmt.setInt(3, quantity);
                        return insertStmt.executeUpdate() > 0;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error adding to cart: " + e.getMessage());
            return false;
        }
    }

    public List<CartItem> getCartItems(int customerId) {
        List<CartItem> cartItems = new ArrayList<>();
        String sql = "SELECT c.cart_id, c.customer_id, c.product_id, p.product_name, p.price, c.quantity " +
                     "FROM cart c JOIN products p ON c.product_id = p.product_id " +
                     "WHERE c.customer_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                CartItem item = new CartItem();
                item.setCartId(rs.getInt("cart_id"));
                item.setCustomerId(rs.getInt("customer_id"));
                item.setProductId(rs.getInt("product_id"));
                item.setProductName(rs.getString("product_name"));
                item.setPrice(rs.getBigDecimal("price"));
                item.setQuantity(rs.getInt("quantity"));
                cartItems.add(item);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching cart items: " + e.getMessage());
        }
        return cartItems;
    }

    public boolean updateCartItemQuantity(int cartId, int quantity) {
        String sql = "UPDATE cart SET quantity = ? WHERE cart_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, quantity);
            pstmt.setInt(2, cartId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating cart item: " + e.getMessage());
            return false;
        }
    }

    public boolean removeFromCart(int cartId) {
        String sql = "DELETE FROM cart WHERE cart_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, cartId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error removing from cart: " + e.getMessage());
            return false;
        }
    }

    public boolean clearCart(int customerId) {
        String sql = "DELETE FROM cart WHERE customer_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, customerId);
            return pstmt.executeUpdate() >= 0;
        } catch (SQLException e) {
            System.err.println("Error clearing cart: " + e.getMessage());
            return false;
        }
    }

    public BigDecimal getCartTotal(int customerId) {
        String sql = "SELECT SUM(p.price * c.quantity) as total " +
                     "FROM cart c JOIN products p ON c.product_id = p.product_id " +
                     "WHERE c.customer_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                BigDecimal total = rs.getBigDecimal("total");
                return total != null ? total : BigDecimal.ZERO;
            }
        } catch (SQLException e) {
            System.err.println("Error calculating cart total: " + e.getMessage());
        }
        return BigDecimal.ZERO;
    }
}
