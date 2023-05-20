package com.electronicstore.dtos;

import com.electronicstore.entity.Order;
import com.electronicstore.entity.Product;
import lombok.*;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrderItemDto {
    private int orderItemId;

    private int quantity;

    private int totalPrice;

    private ProductDto product;

//    private Order order;

}
