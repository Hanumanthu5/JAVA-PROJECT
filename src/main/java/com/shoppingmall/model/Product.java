package com.shoppingmall.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Product {
    private int productId;
    private String productName;
    private String description;
    private BigDecimal price;
    private int stockQuantity;
    private String category;
    private Timestamp createdAt;

    public Product() {
    }

    public Product(int productId, String productName, String description, BigDecimal price, 
                   int stockQuantity, String category) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | %-30s | $%-8.2f | Stock: %-5d | Category: %s",
                productId, productName, price, stockQuantity, category);
    }
}
