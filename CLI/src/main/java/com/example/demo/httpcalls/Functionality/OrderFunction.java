package com.example.demo.httpcalls.Functionality;

import com.example.demo.httpcalls.HttpFunctions;
import com.example.demo.httpcalls.models.OrderItem;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.Scanner;

public class OrderFunction {
    static HttpFunctions httpFunctions = new HttpFunctions();

    String orderURL = "http://localhost:8080/api/order/";
    String orderItemURL = "http://localhost:8080/api/orderItem/";
    String dashboardURL = "http://localhost:8080/api/dashboard/";

    /*
    create automatic then add item to order
    // Create order for a user
    public void createOrder() throws Exception {
        Scanner scanner = new Scanner(System.in);

        int userId = scanner.nextInt();
        String url = orderURL + "create/" + userId;
        JSONObject data = new JSONObject();
        String response = httpFunctions.sendPostRequest(url, data);
        System.out.println("Create Order Response: " + response);
    }*/

    // Add or update order item
    public void addOrUpdateOrderItem(int userId) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter product ID: ");
        int productId = scanner.nextInt();

        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();

        OrderItem orderItem = new OrderItem();
        orderItem.setProductId(productId);
        orderItem.setQuantity(quantity);

        String url = orderItemURL + "addOrUpdate/" + userId;
        JSONObject data = new JSONObject(orderItem);
        String response = null;
        try {
            response = httpFunctions.sendPostRequest(url, data);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Add or Update Order Item Response: " + response);

    }

    // Subtract order item
    public void subtractOrderItem(int userId)  {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter product ID: ");
        int productId = scanner.nextInt();

        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();

        OrderItem orderItem = new OrderItem();
        orderItem.setProductId(productId);
        orderItem.setQuantity(quantity);

        String url = orderItemURL + "subtract/" + userId;
        JSONObject data = new JSONObject(orderItem);
        String response = null;
        try {
            response = httpFunctions.sendPostRequest(url,data);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Subtract Order Item Response: " + response);

        scanner.close();
    }

    // Get order items
    public void getOrderItems(int userID)   {
        String url = orderItemURL + "getOrderItems/" + userID;
        String response = null;
        try {
            response = httpFunctions.sendGetRequest(url);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        response=response.replace('{',' ');
        response=response.replace('}','\n');
        System.out.println("Get Order Items Response: " + response);
    }

    // Get all user completed orders
    public void getUserOrderHistory(int userID)   {
        String url = dashboardURL + "orderHistory/" + userID;
        String response = null;
        try {
            response = httpFunctions.sendGetRequest(url);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        response=response.replace('{',' ');
        response=response.replace('}','\n');
        System.out.println("Get User Order History Response: " + response);
    }
}
