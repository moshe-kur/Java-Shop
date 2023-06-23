package com.shop.DataLayer;

import com.shop.models.OrderItem;
import com.shop.models.Product;
import com.shop.repositories.OrderItemRepository;
import com.shop.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OrderItemService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;


    public OrderItemService(OrderItemRepository orderItemRepository, ProductRepository productRepository) {
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
    }

    public OrderItem addOrUpdateOrderItem(OrderItem orderItem) throws Exception {
        try {
            int orderId = orderItem.getOrderId();
            int productId = orderItem.getProductId();
            int quantity = orderItem.getQuantity();

            if (quantity < 1) {
                throw new IllegalArgumentException("Quantity must be greater than or equal to 1.");
            }
            // Check if the item already exists in the order
            OrderItem existingItem = orderItemRepository.findByOrderIdAndProductId(orderId, productId);

            if (existingItem != null) {
                // Item already exists, update the quantity

                //get product quantity
                Product product = productRepository.findById(productId);
                int originalQuantity= product.getQuantity();

                int newQuantity =existingItem.getQuantity() + quantity;
                if (newQuantity>originalQuantity){
                    throw new IllegalArgumentException("can't add item more then have in stock");
                }else {
                    existingItem.setQuantity(newQuantity);
                    return orderItemRepository.save(existingItem);
                }
            } else {
                // Item does not exist, add a new order item
                return orderItemRepository.save(orderItem);
            }
        } catch (Exception e) {
            logger.error("Failed to add order item: " + e.getMessage());
            throw new Exception("Failed to add order item: " + e.getMessage());
        }
    }

    public void subtractOrderItemQuantity(int orderId, int productId, int quantity) throws  Exception{
        try {
            logger.info("Subtract order item quantity,orderID: " +orderId+", productID: "+productId+", quantity: "+quantity);
            OrderItem orderItem = orderItemRepository.findByOrderIdAndProductId(orderId, productId);
            if (orderItem != null) {
                int currentQuantity = orderItem.getQuantity();
                int updatedQuantity = currentQuantity - quantity;

                if (updatedQuantity <= 0) {
                    // If the updated quantity is less than or equal to 0, remove the order item
                    orderItemRepository.delete(orderItem);
                    logger.info("Delete order item ID: " + orderItem.getOrderItemID());

                } else {
                    // Update the quantity of the order item
                    orderItem.setQuantity(updatedQuantity);
                    orderItemRepository.save(orderItem);
                    logger.info("Subtract order item quantity To: " + updatedQuantity);

                }
            }
        } catch (Exception e) {
            logger.error("Failed to subtract order item quantity: " + e.getMessage());
            throw new Exception("Failed to subtract order item quantity: " + e.getMessage());
        }
    }

    public void deleteOrderItem(int orderItemId) {
        try {
            orderItemRepository.deleteById(orderItemId);
        } catch (Exception e) {
            logger.error("Failed to delete order item: " + e.getMessage());
            throw new RuntimeException("Failed to delete order item: " + e.getMessage());
        }
    }
/*
    public List<OrderItem> getOrderItemsByOrderId(int orderId) {
        try {
            return orderItemRepository.findByOrderId(orderId);
        } catch (Exception e) {
            logger.error("Failed to get order items by order ID: " + e.getMessage());
            throw new RuntimeException("Failed to get order items by order ID: " + e.getMessage());
        }
    }

    public List<OrderItem> getAllOrderItems() {
        try {
            return orderItemRepository.findAll();
        } catch (Exception e) {
            logger.error("Failed to get all order items: " + e.getMessage());
            throw new RuntimeException("Failed to get all order items: " + e.getMessage());
        }
    }
    */
}
