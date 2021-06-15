package com.shopping.db;

import java.util.Date;

public class Order {
    private int orderId;
    private int userId;
    private int noOfItems;
    private double amount;
    private Date orderDate;

    public Order() {
    }

    public Order(int orderId, int userId, int noOfItems, double amount, Date orderDate) {
        this.orderId = orderId;
        this.userId = userId;
        this.noOfItems = noOfItems;
        this.amount = amount;
        this.orderDate = orderDate;
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

    public int getNoOfItems() {
        return noOfItems;
    }

    public void setNoOfItems(int noOfItems) {
        this.noOfItems = noOfItems;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", noOfItems=" + noOfItems +
                ", amount=" + amount +
                ", orderDate=" + orderDate +
                '}';
    }
}
