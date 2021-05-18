package com.example.springcashier;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
class Card {

    private Long id;
    private String cardNumber;
    private String cardCode;
    private double balance;
    private boolean activated;
    private String status;
    private int reward;

}