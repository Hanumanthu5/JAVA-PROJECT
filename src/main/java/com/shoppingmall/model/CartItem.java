package com.shoppingmall.model;

import java.math.BigDecimal;

public class CartItem {
    private int cartId;
    private int customerId;
    private int productId;
    private String productName;
    private BigDecimal price;
    private int quantity;

    public CartItem() {
    }

    public CartItem(int cartId, int customerId, int productId, String productName, 
                   BigDecimal price, int quantity) {
        this.cartId = cartId;
        this.customerId = customerId;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSubtotal() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    @Override
    public String toString() {
        return String.format("%-30s | $%-8.2f x %-3d = $%.2f",
                productName, price, quantity, getSubtotal());
    }
}
