package com.electronicstore.repository;

import com.electronicstore.entity.Cart;
import com.electronicstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface  CartRepository extends JpaRepository<Cart,String> {

    Optional<Cart> findByCart(User user);
}
