package com.example.springcashier;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
class Order {

    private Long id;
    private String drink;
    private String milk;
    private String size;
    private double total;
    private String status;

}