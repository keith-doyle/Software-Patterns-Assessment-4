package com.clothesstore.model;

import com.clothesstore.pattern.state.OrderState;
import com.clothesstore.pattern.state.OrderStateFactory;

import java.sql.Timestamp;
import java.util.List;

public class Order {
    private int orderId;
    private int userId;
    private double totalPrice;
    private String status;
    private Timestamp createdAt;

    public Order() {
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public OrderState getStateObject() {
        return OrderStateFactory.fromString(status);
    }

    public List<String> getAllowedTransitions() {
        return getStateObject().getAllowedTransitions();
    }

    public boolean canTransitionTo(String newStatus) {
        return getAllowedTransitions().contains(newStatus);
    }
}