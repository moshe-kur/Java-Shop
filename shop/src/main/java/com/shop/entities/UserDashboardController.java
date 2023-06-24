package com.shop.entities;

import com.shop.DataLayer.OrderItemService;
import com.shop.DataLayer.OrderService;
import com.shop.DataLayer.UserService;
import com.shop.models.Order;
import com.shop.models.OrderItem;
import com.shop.models.User;
import com.shop.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/dashboard")
public class UserDashboardController {
    private final OrderService orderService;
    private final OrderItemService orderItemService;
    private final UserService userService;
    private final UserRepository userRepository;

    public UserDashboardController(OrderService orderService, OrderItemService orderItemService, UserService userService, UserRepository userRepository) {
        this.orderService = orderService;
        this.orderItemService = orderItemService;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/orderHistory/{userID}")
    public ResponseEntity viewOrderHistory(@PathVariable int userID) {
        try {
            // Retrieve the order history for the user
            List<Order> orderHistory = orderService.getAllUserSendOrders(userID);

            // Convert the order history to a string representation
            StringBuilder response = new StringBuilder();
            for (Order order : orderHistory) {
                if (order.isSend()) {
                    response.append("Order ID: ").append(order.getOrderId()).append("\n");
                    response.append("Order Date: ").append(order.getOrderDate()).append("\n");
                    response.append("Total Amount: ").append(order.getTotalAmount()).append("\n");
                    response.append("Status: ").append(order.isSend() ? "Sent" : "Not Sent").append("\n");

                    List<OrderItem> orderItems = orderItemService.getOrderItemsByOrderId(order.getOrderId());

                    // Append order items
                    response.append("Order Items:\n");
                    for (OrderItem orderItem : orderItems) {
                        response.append("- Product ID: ").append(orderItem.getProductId()).append(", ")
                                .append("Quantity: ").append(orderItem.getQuantity()).append(", ")
                                .append("Price: ").append(orderItem.getItemPrice()).append("\n");
                    }

                    response.append("\n"); }
            }
            // Return the order history as a string
            return ResponseEntity.ok(response.toString());
        }catch (Exception e){
            return sendError(e.getMessage());
        }
    }

    @GetMapping("/accountDetails/{userID}")
    public ResponseEntity viewAccountDetails(@RequestParam int userID){
        try {
            User user = userRepository.findById(userID);

            // Convert the account details to a string representation
            StringBuilder response = new StringBuilder();
            response.append("Name: ").append(user.getName()).append("\n")
                    .append("Email: ").append(user.getEmail()).append("\n");

            // Return the account details as a string
            return ResponseEntity.ok(response.toString());
        }catch (Exception e){
            return sendError(e.getMessage());
        }
    }
    @PostMapping("/updatePersonalInfo/{userID}")
    public ResponseEntity updatePersonalInfo(@RequestBody User updatedUser,@RequestParam int userID) {
        try {
            User user = userRepository.findByEmail(updatedUser.getEmail());

            // Update the personal information of the user
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());

            // Save the updated user details
            userRepository.save(user);

            // Return a success message
            return ResponseEntity.ok("Personal information updated successfully.");
        } catch (Exception e) {
            return sendError(e.getMessage());
        }
    }

    @PostMapping("/changePassword/{userID}")
    public ResponseEntity changePassword(@RequestBody Map<String, Object> request,
                                         @RequestParam int userID) {
        try {
            String currentPassword = (String) request.get("currentPassword");
            String newPassword = (String) request.get("newPassword");
            // Retrieve the currently logged-in user
            User user = userRepository.findById(userID);

            // Check if the current password matches
            if (!user.getPassword().equals(currentPassword)) {
                return sendError("Current password is incorrect.");
            }
            // Update the password of the user
            user.setPassword(newPassword);

            // Save the updated user details
            userRepository.save(user);

            // Return a success message
            return ResponseEntity.ok("Password changed successfully.");
        } catch (Exception e) {
            return sendError(e.getMessage());
        }
    }

    @PostMapping("/checkout/{orderID}")
    public ResponseEntity<String> checkoutOrder(@RequestParam int orderID) {
        try {
            String confirmationMessage = orderService.checkout(orderID);
            return ResponseEntity.ok(confirmationMessage);
        } catch (Exception e) {
            return sendError(e.getMessage());
        }
    }

    public ResponseEntity<String> sendError(String str) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(str);
    }
}
