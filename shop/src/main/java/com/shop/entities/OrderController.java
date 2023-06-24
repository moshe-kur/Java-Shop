package com.shop.entities;

import com.shop.DataLayer.OrderService;
import com.shop.models.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

/*
//create automatic then add order item
    @PostMapping("/create/{userID}")
    public ResponseEntity<String> createOrder(@PathVariable int userID) {
        try {
            Order createdOrder = orderService.getOrCreateOrder(userID);
            return ResponseEntity.ok(createdOrder.toString());
        }catch (Exception e){
            return sendError(e.getMessage());
        }
    }*/

    @PutMapping("/update/{orderId}")
    public ResponseEntity<String> updateOrder(@PathVariable int orderId, @RequestBody Order updatedOrder) {
        try {
            updatedOrder.setOrderId(orderId);
            Order updated = orderService.updateOrder(updatedOrder);
            if (updated != null) {
                return ResponseEntity.ok("Updated: "+updated.toString());
            } else {
                return sendError("Product NOT updated");
            }
        } catch (Exception e) {
            return sendError(e.getMessage());
        }
    }

    /*
    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable int orderId) {
        try {
            String stringBack = orderService.deleteOrder(orderId);
            return ResponseEntity.ok(stringBack);
        } catch (Exception e) {
            return sendError(e.getMessage());
        }
    }*/
    @GetMapping("/getAllUserCompleted/{userID}")
    public ResponseEntity<?> getAllUserOrders(@PathVariable int userID) {
        try {
            List<Order> orders = orderService.getAllUserSendOrders(userID);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return sendError(e.getMessage());
        }
    }


    //function to send error back to client
    public ResponseEntity<String> sendError(String str) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(str);
    }
}
