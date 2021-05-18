package com.example.springcashier;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
class OrderResponse {

    private String orderNumber;
    private Long id;
    private String drink;
    private String milk;
    private String size;
    private double total;
    private String status;    

    public OrderResponse(Order order, String orderNumber) {

        this.orderNumber = orderNumber;
        this.id = order.getId();
        this.drink = order.getDrink();
        this.milk = order.getMilk();
        this.size = order.getSize();
        this.total = order.getTotal();
        this.status = order.getStatus();
    }

}