package com.shop.entities;

import com.shop.DataLayer.OrderItemService;
import com.shop.DataLayer.OrderService;
import com.shop.DataLayer.ProductService;
import com.shop.models.Order;
import com.shop.models.OrderItem;
import com.shop.models.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orderItem")
public class OrderItemController {
    private final OrderItemService orderItemService;
    private final OrderService orderService;
    private final ProductService productService;

    public OrderItemController(OrderItemService orderItemService, OrderService orderService, ProductService productService) {
        this.orderItemService = orderItemService;
        this.orderService = orderService;
        this.productService = productService;
    }


    @PostMapping("/addOrUpdate/{userID}")
    public ResponseEntity<String> addOrUpdateOrderItem(@PathVariable int userID, @RequestBody OrderItem orderItem) {
        try {
            // Check if there is an open order for the user
            Order order = orderService.getOrCreateOrder(userID);

            // Get the product details from the database
            Product product = productService.getProductById(orderItem.getProductId());

            // Set the order ID for the order item
            orderItem.setOrderId(order.getOrderId());

            //update amount
            float itemPrice = product.getPrice();
            orderItem.setItemPrice(itemPrice);


            OrderItem addedOrderItem = orderItemService.addOrUpdateOrderItem(orderItem);
            return ResponseEntity.ok(addedOrderItem.toString());
        } catch (Exception e) {
            return sendError(e.getMessage());
        }
    }


    @PostMapping("/subtract/{orderId}/{productId}/{quantity}")
    public ResponseEntity<String> subtractOrderItemQuantity(@PathVariable int orderId, @PathVariable int productId, @PathVariable int quantity) {
        try {
            orderItemService.subtractOrderItemQuantity(orderId, productId, quantity);
            return ResponseEntity.ok("Order item quantity subtracted successfully.");
        } catch (Exception e) {
            return sendError(e.getMessage());
        }
    }

    //function to send error back to client
    public ResponseEntity<String> sendError(String str) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(str);
    }
}
