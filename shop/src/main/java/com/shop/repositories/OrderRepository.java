package com.shop.repositories;

import com.shop.models.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order,Integer> {
    Order findByUserID(int userID);
    List<Order> findAllByUserID(int userID);
}
