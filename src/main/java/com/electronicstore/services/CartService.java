package com.electronicstore.services;

import com.electronicstore.dtos.AddItemToCartRequest;
import com.electronicstore.dtos.CartDto;

public interface CartService {
    CartDto addItemToCart(String userId, AddItemToCartRequest request);

    // void remove Item  from cart:
    void removeItemFromCart(String userId,int cartItem);


    // clear cart

    void clearCart(String userId);

    CartDto getCartByUser(String userId);
}
