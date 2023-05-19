package com.electronicstore.entity;

import lombok.*;

import javax.persistence.*;
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

     private String productImageName;

     @ManyToOne(fetch = FetchType.EAGER)
     @JoinColumn(name = "categoryId")
     private Category category;





}
