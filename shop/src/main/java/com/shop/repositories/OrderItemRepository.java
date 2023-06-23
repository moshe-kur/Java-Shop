package com.shop.repositories;

import com.shop.models.OrderItem;
import org.springframework.data.repository.CrudRepository;

public interface OrderItemRepository extends CrudRepository<OrderItem,Integer> {
    OrderItem findByOrderIdAndProductId(int orderId, int productId);
}
