package com.electronicstore.services;

import com.electronicstore.dtos.AddItemToCartRequest;
import com.electronicstore.dtos.CartDto;

public interface CartService {

    // Add item to the cart
    // Cart for user is not available : we will create the cart and then add it to the cart.
    // If cart is available then add the item into the cart.
    CartDto addItemToCart(String userId, AddItemToCartRequest request);

    // void remove Item  from cart:
    void removeItemFromCart(String userId,int cartItem);


    // clear cart
    void clearCart(String userId);

    CartDto getCartByUser(String userId);
}
