package com.electronicstore.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Product {
    @Id
     private  String productId;

     private  String title;

     @Column(length = 1000)
     private String description;

     private int price;

    private int discountedPrice;


     private int quantity;

     private Date addedDate;

     private boolean live;

     private  boolean stock;




}
