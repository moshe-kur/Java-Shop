package com.example.demo;

import com.example.demo.httpcalls.Functionality.ManagerFunction;
import com.example.demo.httpcalls.Functionality.OrderFunction;
import com.example.demo.httpcalls.Functionality.UserFunction;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		//SpringApplication.run(DemoApplication.class, args);

		ManagerFunction managerFunction = new ManagerFunction();
		UserFunction userFunction = new UserFunction();
		OrderFunction orderFunction =new OrderFunction();
		Scanner scanner = new Scanner(System.in);

		int userID = 0;

		boolean run = true;
		try {
			while (run){
				System.out.println("=== Main Menu ===");
				System.out.println("1. Register Manager");
				System.out.println("1. Register Customer");
				System.out.println("3. login");
				System.out.println("4. Manager");
				System.out.println("5. Customer");
				System.out.println("6. Exit");
				System.out.print("Enter your choice: ");
				int choice = scanner.nextInt();

				switch (choice) {
					case 1:
						userFunction.registerManager();
						break;
					case 2:
						userFunction.registerBuyer();
						break;
					case 3:
						userID = userFunction.login();
						break;
					case 4:
						managerMenu(managerFunction, scanner);
						break;
					case 5:
						customerMenu(orderFunction,scanner,userID);
						break;
					case 6:
						run = false;
						break;
					default:
						System.out.println("Invalid choice. Please try again.");
				}
			}
		} catch (Exception e) {
			e.getMessage();
		}
	}
	private static void managerMenu(ManagerFunction managerFunction, Scanner scanner) {
		boolean back = false;
		while (!back) {
			System.out.println();
			System.out.println("=== Manager Menu ===");
			System.out.println("1. Add Product");
			System.out.println("2. Update Product");
			System.out.println("3. Delete Product");
			System.out.println("4. Get All Products");
			System.out.println("5. Back to Main Menu");
			System.out.print("Enter your choice: ");
			int choice = scanner.nextInt();

			switch (choice) {
				case 1:
					managerFunction.addProduct();
					break;
				case 2:
					managerFunction.updateProduct();
					break;
				case 3:
					managerFunction.deleteProduct();
					break;
				case 4:
					managerFunction.getAllProducts();
					break;
				case 5:
					back = true;
					break;
				default:
					System.out.println("Invalid choice. Please try again.");
			}
		}
	}

	private static void customerMenu(OrderFunction orderFunction, Scanner scanner,int userID) throws Exception {
		boolean back = false;
		while (!back) {
			System.out.println("\n=== Customer Menu ===");
			System.out.println("1. Add Item to Order");
			System.out.println("2. Subtract Item from Order");
			System.out.println("3. Get Order Items");
			System.out.println("4. Get User Order History");
			System.out.println("5. Back to Main Menu");
			System.out.print("Enter your choice: ");
			int choice = scanner.nextInt();

			switch (choice) {
				case 1:
					orderFunction.addOrUpdateOrderItem(userID);
					break;
				case 2:
					orderFunction.subtractOrderItem(userID);
					break;
				case 3:
					orderFunction.getOrderItems(userID);
					break;
				case 4:
					orderFunction.getUserOrderHistory(userID);
					break;
				case 5:
					back = true;
					break;
				default:
					System.out.println("Invalid choice. Please try again.");
			}
		}
	}

}
