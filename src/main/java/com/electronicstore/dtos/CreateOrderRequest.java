package com.electronicstore.dtos;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CreateOrderRequest {

    @NotBlank(message = "Cart id is required")
    private String cartId;
    @NotBlank(message = "User id is required")

    private String userId;

    private String orderStatus="PENDING";
    private String paymentStatus="NOT PAID";

    @NotBlank(message = "Address  is required")
    private String billingAddress;
    @NotBlank(message = "phone number  is required")
    private String billingPhone;
    @NotBlank(message = "billingName  is required")
    private String billingName;

}
