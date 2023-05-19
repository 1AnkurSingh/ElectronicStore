package com.electronicstore.services.imp;

import com.electronicstore.dtos.AddItemToCartRequest;
import com.electronicstore.dtos.CartDto;
import com.electronicstore.entity.Cart;
import com.electronicstore.entity.CartItem;
import com.electronicstore.entity.Product;
import com.electronicstore.entity.User;
import com.electronicstore.exception.ResourceNotFoundException;
import com.electronicstore.repository.CartRepository;
import com.electronicstore.repository.ProductRepository;
import com.electronicstore.repository.UserRepository;
import com.electronicstore.services.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;


@Service
public class CartServiceImp implements CartService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    ModelMapper mapper;



    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) {

        int quantity = request.getQuantity();
        String productId = request.getProductId();
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product id not found"));

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("UserId Not found in database"));

        Cart cart = null;

        try{
            Cart cart1 = cartRepository.findByCart(user).get();
        }catch (NoSuchElementException ex){
            cart=new Cart();
            cart.setCreatedAt(new Date());
        }

        // perform cart operation
//        List<CartItem> items = cart.getItems();
        // create items

        CartItem cartItem = CartItem.builder()
                .quantity(quantity)
                .totalPrice(quantity * product.getPrice())
                .cart(cart)
                .product(product).build();
        cart.getItems().add(cartItem);
        cart.setUser(user);
        Cart updatedCart = cartRepository.save(cart);


        return mapper.map(updatedCart, CartDto.class);
    }

    @Override
    public void removeItemFromCart(String userId, int cartItem) {

    }

    @Override
    public void clearCart(String userId) {

    }
}
