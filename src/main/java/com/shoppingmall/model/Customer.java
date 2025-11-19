package com.shoppingmall.model;

import java.sql.Timestamp;

public class Customer {
    private int customerId;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private Timestamp createdAt;

    public Customer() {
    }

    public Customer(int customerId, String username, String password, String fullName, 
                   String email, String phone, String address) {
        this.customerId = customerId;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return String.format("Customer[ID=%d, Username=%s, Name=%s, Email=%s]",
                customerId, username, fullName, email);
    }
}
