package com.electronicstore.Controller;

import com.electronicstore.dtos.ApiResponseMessage;
import com.electronicstore.dtos.CreateOrderRequest;
import com.electronicstore.dtos.OrderDto;
import com.electronicstore.dtos.PageableResponse;
import com.electronicstore.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping()
    public ResponseEntity<OrderDto>createOrder(@Valid @RequestBody CreateOrderRequest request )
    {
        OrderDto order = orderService.createOrder(request);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponseMessage>removeOrder(@PathVariable String orderId){
        orderService.removeOrder(orderId);
        ApiResponseMessage responceMessage = ApiResponseMessage.builder()
                .message("Order deleted Successfully!!")
                .success(true)
                .status(HttpStatus.OK).build();
        return new ResponseEntity<>(responceMessage,HttpStatus.OK);

    }
    // get order of the user


    @GetMapping("/users/{userId}")
    public ResponseEntity<List<OrderDto>>getOrdersOfUser(@PathVariable String userId){
        List<OrderDto> orderOfUser = orderService.getOrdersOfUser(userId);
        return new ResponseEntity<>(orderOfUser,HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<PageableResponse<OrderDto>>getOrders(
                                                               @RequestParam (value = "pageNumber",defaultValue = "0" , required = false) int pageNumber,
                                                               @RequestParam(value = "pageSize" ,defaultValue = "10",required = false)int pageSize,
                                                               @RequestParam(value = "sortBy" ,defaultValue = "orderdDate",required = false)String sortBy,
                                                               @RequestParam(value = "sortDir" ,defaultValue = "dsc",required = false)String sortDir


    ){
        PageableResponse<OrderDto> orders = orderService.getOrders(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }
}
