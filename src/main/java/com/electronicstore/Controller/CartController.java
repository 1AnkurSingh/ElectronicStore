package com.electronicstore.Controller;

import com.electronicstore.dtos.AddItemToCartRequest;
import com.electronicstore.dtos.ApiResponseMessage;
import com.electronicstore.dtos.CartDto;
import com.electronicstore.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/{userId}")
    public ResponseEntity<CartDto>addItemToCart(@PathVariable ("userId") String userId,@RequestBody AddItemToCartRequest request){
        CartDto cartDto = cartService.addItemToCart(userId, request);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/item/{itemId}")
    public ResponseEntity<ApiResponseMessage>removeItemFromCart(@PathVariable String userId,@PathVariable int itemId){
        cartService.removeItemFromCart(userId,itemId);
        ApiResponseMessage apiResponse = ApiResponseMessage.builder()
                .message("Item is removed Successfully!!")
                .success(true)
                .status(HttpStatus.OK).build();
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);


    }

    @DeleteMapping("/{userId}/clearCart")
    public ResponseEntity<ApiResponseMessage>clearCart(@PathVariable String userId){
        cartService.clearCart(userId);
        ApiResponseMessage apiResponse = ApiResponseMessage.builder()
                .message("Now Cart is blank")
                .success(true)
                .status(HttpStatus.OK).build();
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);

    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartDto>getCart(@PathVariable ("userId") String userId){
        CartDto cartDto = cartService.getCartByUser(userId);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }



}
