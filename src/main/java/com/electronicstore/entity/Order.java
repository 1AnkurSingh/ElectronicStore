package com.electronicstore.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "orders")
public class Order {

    @Id
    private String oderId;


    //pending
    //Dispatch
    //delivered
    //enum
    private String orderStatus;

    // not paid
    //paid
    // boolean => false , not paid id => if => true paid
    // enum
    private String paymentStatus;

    private int orderAmount;

    @Column(length = 1000)
    private String billingAddress;

    private String billingPhone;

    private String billingName;

    private Date orderdDate;

    private Date deliverdDate;

    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<OrderItem>orderItems= new ArrayList<>();
}
