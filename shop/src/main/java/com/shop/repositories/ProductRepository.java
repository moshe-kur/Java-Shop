package com.shop.repositories;

import com.shop.models.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product,Integer> {
    Product findById(int productID);
}
