package com.shoppingmall.dao;

import com.shoppingmall.model.Customer;
import com.shoppingmall.util.DatabaseConnection;

import java.sql.*;

public class CustomerDAO {

    public Customer authenticate(String username, String password) {
        String sql = "SELECT * FROM customers WHERE username = ? AND password = ?";
        Customer customer = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                customer = extractCustomerFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error authenticating customer: " + e.getMessage());
        }
        return customer;
    }

    public boolean registerCustomer(Customer customer) {
        String sql = "INSERT INTO customers (username, password, full_name, email, phone, address) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, customer.getUsername());
            pstmt.setString(2, customer.getPassword());
            pstmt.setString(3, customer.getFullName());
            pstmt.setString(4, customer.getEmail());
            pstmt.setString(5, customer.getPhone());
            pstmt.setString(6, customer.getAddress());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error registering customer: " + e.getMessage());
            return false;
        }
    }

    public Customer getCustomerById(int customerId) {
        String sql = "SELECT * FROM customers WHERE customer_id = ?";
        Customer customer = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                customer = extractCustomerFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching customer: " + e.getMessage());
        }
        return customer;
    }

    public boolean updateCustomer(Customer customer) {
        String sql = "UPDATE customers SET full_name = ?, email = ?, phone = ?, address = ? WHERE customer_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, customer.getFullName());
            pstmt.setString(2, customer.getEmail());
            pstmt.setString(3, customer.getPhone());
            pstmt.setString(4, customer.getAddress());
            pstmt.setInt(5, customer.getCustomerId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating customer: " + e.getMessage());
            return false;
        }
    }

    public boolean usernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM customers WHERE username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error checking username: " + e.getMessage());
        }
        return false;
    }

    private Customer extractCustomerFromResultSet(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setCustomerId(rs.getInt("customer_id"));
        customer.setUsername(rs.getString("username"));
        customer.setPassword(rs.getString("password"));
        customer.setFullName(rs.getString("full_name"));
        customer.setEmail(rs.getString("email"));
        customer.setPhone(rs.getString("phone"));
        customer.setAddress(rs.getString("address"));
        customer.setCreatedAt(rs.getTimestamp("created_at"));
        return customer;
    }
}
