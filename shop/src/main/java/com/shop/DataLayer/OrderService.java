package com.shop.DataLayer;

import com.shop.models.Order;
import com.shop.models.OrderItem;
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
    private final OrderItemService orderItemService;

    public OrderService(OrderRepository orderRepository, OrderItemService orderItemService) {
        this.orderRepository = orderRepository;
        this.orderItemService = orderItemService;
    }
    public Order getOrCreateOrder(int userId) throws Exception {
        try {
            List<Order> order = orderRepository.findAllByUserID(userId);
            order.removeIf(Order::isSend);

            //check if not found order or order already send
            if (order.isEmpty()) {
                logger.info("Create new order to user number: "+userId);
                // Create a new order
                Calendar calendar = Calendar.getInstance();
                Timestamp currentDate = new Timestamp(System.currentTimeMillis());
                Order neworder = new Order(userId, currentDate ,0,false);
                orderRepository.save(neworder);
                logger.info("Create order number: "+neworder.getOrderId());
                return neworder;
            }else {
                return order.get(0);
            }

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
            orderList.removeIf(order ->!order.isSend() );
            //orderList.removeIf(Order::isSend);
            return orderList;
        } catch (Exception e) {
            logger.error("Failed get Order: " + e.getMessage());
            throw new Exception("Failed get Order: "+ e.getMessage());
        }
    }

    public String checkout(int orderID) throws Exception {
        try {
            // Get the user's cart or open order
            Order order = getOrderById(orderID);
            List<OrderItem> orderItemList = orderItemService.getOrderItemsByOrderId(order.getOrderId());

            //calculate total amount
            float totalAmount = 0.0f;
            for (OrderItem orderItem : orderItemList) {
                totalAmount += orderItem.getQuantity() * orderItem.getItemPrice();
            }
            order.setTotalAmount(totalAmount);

            // Update the order status to indicate it's been sent
            order.setIsSend(true);

            // Save the updated order
            orderRepository.save(order);

            // Generate order confirmation message
            String confirmationMessage = "Order confirmed, Order ID: "+order.getOrderId()+
                    " send. with total amount: "+order.getTotalAmount();

            return confirmationMessage;
        } catch (Exception e) {
            throw new Exception("Error during checkout: " + e.getMessage());
        }
    }


}
