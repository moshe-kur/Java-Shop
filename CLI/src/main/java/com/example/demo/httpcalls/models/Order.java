package com.example.demo.httpcalls.models;

import jakarta.persistence.*;

import java.sql.Timestamp;


@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderID")
    private int orderId;

    @Column(name = "userID", nullable = false)
    private int userID;
    @Column(name = "orderDate", nullable = false)
    private Timestamp orderDate;
    @Column(name = "totalAmount", nullable = false)
    private float totalAmount;
    @Column(name = "isSend", nullable = false)
    private boolean isSend = false;
    @ManyToOne
    @JoinColumn(name = "userID", referencedColumnName = "userID", insertable = false, updatable = false)
    private User user;


    public Order() {
    }

    public Order(int userID,Timestamp orderDate , float totalAmount,boolean isSend) {
        this.userID = userID;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.isSend = isSend;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }


    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isSend() {
        return isSend;
    }

    public void setIsSend(boolean send) {
        isSend = send;
    }
    @Override
    public String toString() {
        return "orderId: " + orderId +
                ", userId: " + userID +
                ", orderDate: " + orderDate +
                ", totalAmount: " + totalAmount +
                ", isSend=" + isSend;
    }
}
