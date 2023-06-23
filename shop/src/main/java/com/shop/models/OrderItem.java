package com.shop.models;

import jakarta.persistence.*;

@Entity
@Table(name = "OrderItems")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderItemID")
    private int orderItemID;

    @Column(name = "orderID", nullable = false)
    private int orderId;

    @Column(name = "productID", nullable = false)
    private int productId;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "itemPrice", nullable = false)
    private float itemPrice;
    @ManyToOne
    @JoinColumn(name = "orderID", referencedColumnName = "orderID", insertable = false, updatable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "productID", referencedColumnName = "productID", insertable = false, updatable = false)
    private Product product;

    public OrderItem() {
    }

    public OrderItem(int orderId, int productId, int quantity, float itemPrice) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.itemPrice = itemPrice;
    }

    public int getOrderItemID() {
        return orderItemID;
    }


    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(float itemPrice) {
        this.itemPrice = itemPrice;
    }

    @Override
    public String toString() {
        return "orderItemID: " + orderItemID +
                ", orderId: " + orderId +
                ", productId: " + productId +
                ", quantity: " + quantity +
                ", itemPrice: " + itemPrice;
    }
}
