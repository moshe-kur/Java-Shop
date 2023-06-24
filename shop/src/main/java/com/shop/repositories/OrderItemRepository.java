package com.shop.repositories;

import com.shop.models.OrderItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderItemRepository extends CrudRepository<OrderItem,Integer> {
    OrderItem findByOrderIdAndProductId(int orderId, int productId);
    List<OrderItem> findByOrderId(int orderId);
}
