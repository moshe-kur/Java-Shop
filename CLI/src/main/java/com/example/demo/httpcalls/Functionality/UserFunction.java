package com.example.demo.httpcalls.Functionality;

import com.example.demo.httpcalls.HttpFunctions;
import com.example.demo.httpcalls.models.Buyer;
import com.example.demo.httpcalls.models.Manager;
import org.json.JSONObject;

import java.util.Scanner;

public class UserFunction {
    static HttpFunctions httpFunctions = new HttpFunctions();

    String mainURL = "http://localhost:8080/api/users/";


     public void registerManager() {
        try {
            String Url = mainURL+"registerManager";

            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter manager name: ");
            String name = scanner.nextLine();

            System.out.print("Enter manager email: ");
            String email = scanner.nextLine();

            System.out.print("Enter manager password: ");
            String password = scanner.nextLine();

            Manager manager = new Manager(name,email,password);
            JSONObject json = new JSONObject(manager);

            String Response = httpFunctions.sendPostRequest(Url, json);
            System.out.println("Registration Response: " + Response);

            //scanner.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void registerBuyer() {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter buyer name: ");
            String name = scanner.nextLine();

            System.out.print("Enter buyer email: ");
            String email = scanner.nextLine();

            System.out.print("Enter buyer password: ");
            String password = scanner.nextLine();

            String url = mainURL + "registerBuyer";
            Buyer buyer = new Buyer(name, email, password);
            JSONObject json = new JSONObject(buyer);

            String response = httpFunctions.sendPostRequest(url, json);
            System.out.println("Registration Response: " + response);

            //scanner.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public int login()  {
         int userID = 0;
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter user email: ");
            String email = scanner.nextLine();

            System.out.print("Enter user password: ");
            String password = scanner.nextLine();

            String url = mainURL + "login";
            JSONObject json = new JSONObject();
            json.put("email", email);
            json.put("password", password);

            String response = httpFunctions.sendPostRequest(url, json);
            System.out.println("Login Response: " + response);

            userID = Integer.parseInt(response);
            //scanner.close();
        } catch (Exception e) {
          System.out.println(e.getMessage());
        }
        return userID;
    }


}
