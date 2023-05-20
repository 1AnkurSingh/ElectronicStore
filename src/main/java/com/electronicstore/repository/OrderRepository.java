package com.electronicstore.repository;

import com.electronicstore.entity.Order;
import com.electronicstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,String>


{
    List<Order>findByUser(User user);

}
