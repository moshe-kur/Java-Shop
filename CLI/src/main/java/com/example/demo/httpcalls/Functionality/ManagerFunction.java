package com.example.demo.httpcalls.Functionality;

import com.example.demo.httpcalls.HttpFunctions;
import com.example.demo.httpcalls.models.Product;
import org.json.JSONObject;

import java.util.Scanner;

public class ManagerFunction {
    static HttpFunctions httpFunctions = new HttpFunctions();

    String mainURL = "http://localhost:8080/api/products/";


    public void addProduct() {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter product name: ");
            String name = scanner.nextLine();

            System.out.print("Enter product description: ");
            String description = scanner.nextLine();

            System.out.print("Enter product price: ");
            float price = scanner.nextFloat();

            System.out.print("Enter product quantity: ");
            int quantity = scanner.nextInt();

            // Create a Product object with the provided details
            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setQuantity(quantity);

            // Send a POST request to the server to add the product
            String url = mainURL + "addProduct";
            JSONObject json = new JSONObject(product);
            String response = httpFunctions.sendPostRequest(url, json);
            System.out.println("Add Product Response: " + response);

            //scanner.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateProduct() {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter product ID to update: ");
            int productId = scanner.nextInt();
            scanner.nextLine(); // Consume the remaining newline character

            System.out.print("Enter updated product name: ");
            String name = scanner.nextLine();

            System.out.print("Enter updated product description: ");
            String description = scanner.nextLine();

            System.out.print("Enter updated product price: ");
            float price = scanner.nextFloat();

            System.out.print("Enter updated product quantity: ");
            int quantity = scanner.nextInt();

            // Create a Product object with the updated details
            Product product = new Product();
            product.setProductID(productId);
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setQuantity(quantity);

            // Send a PUT request to the server to update the product
            String url = mainURL + "updateProduct/" + productId;
            JSONObject json = new JSONObject(product);
            String response = httpFunctions.sendPutRequest(url, json);
            System.out.println("Update Product Response: " + response);

            //scanner.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteProduct() {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter product ID to delete: ");
            int productId = scanner.nextInt();

            // Send a DELETE request to the server to delete the product
            String url = mainURL + "deleteProduct/" + productId;
            String response = httpFunctions.sendDeleteRequest(url);
            System.out.println("Delete Product Response: " + response);

            //scanner.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void getAllProducts() {
        try {
            // Send a GET request to the server to retrieve all products
            String url = mainURL + "getAllProduct";
            String response = httpFunctions.sendGetRequest(url);
            response=response.replace('{',' ');
            response=response.replace('}','\n');
            System.out.println("Get All Products Response: " + response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
