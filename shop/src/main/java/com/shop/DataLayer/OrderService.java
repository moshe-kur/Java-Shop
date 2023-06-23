package com.shop.DataLayer;

import com.shop.models.Order;
import com.shop.repositories.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

@Service
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    public Order getOrCreateOrder(int userId) throws Exception {
        try {
            Order order = orderRepository.findByUserID(userId);
            //check if not found order or order already send
            if (order == null || order.isSend()) {
                logger.info("Create new order to user number: "+userId);
                // Create a new order
                Calendar calendar = Calendar.getInstance();
                Timestamp currentDate = new Timestamp(System.currentTimeMillis());
                order = new Order(userId, currentDate ,0,false);
                orderRepository.save(order);
                logger.info("Create order number: "+order.getOrderId());
            }
            return order;
        } catch (Exception e) {
            logger.error("Failed get or Create Order to userID: "+userId+ e.getMessage());
            throw new Exception("Failed to add product: "+ e.getMessage());
        }
    }
    public Order getOrderById(int orderId) throws Exception{
       try {
           Order order = orderRepository.findById(orderId).orElse(null);
           return order;
       } catch (Exception e) {
           logger.error("Failed get Order: " + e.getMessage());
           throw new Exception("Failed get Order: "+ e.getMessage());
       }
    }
    public Order updateOrder(Order order) throws Exception {
        try {
            logger.info("Order {} Updated",order.getOrderId());
            Order orderUp = orderRepository.save(order);
            return orderUp;
        }catch (Exception e) {
            logger.error("Failed delete Order: " + e.getMessage());
            throw new Exception("Failed delete Order: "+ e.getMessage());
        }
    }

    public String deleteOrder(int orderId) throws Exception{
        try {
            logger.info("Order {} deleted",orderId);
             orderRepository.deleteById(orderId);
             return "Order deleted: "+orderId;
        }catch (Exception e) {
            logger.error("Failed delete Order: " + e.getMessage());
            throw new Exception("Failed delete Order: "+ e.getMessage());
        }
    }

    public List<Order> getAllUserSendOrders(int userID) throws Exception{
        try {
            //check if user id or ids
            List<Order> orderList = (List<Order>) orderRepository.findAllByUserID(userID);
            //orderList.removeIf(order ->order.isSend() );
            orderList.removeIf(Order::isSend);
            return orderList;
        } catch (Exception e) {
            logger.error("Failed get Order: " + e.getMessage());
            throw new Exception("Failed get Order: "+ e.getMessage());
        }
    }
}
