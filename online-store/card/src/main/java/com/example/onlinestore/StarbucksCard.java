package com.example.onlinestore;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;



@Data
@RequiredArgsConstructor
class StarbucksCard {

    private @Id @GeneratedValue Long id;

    private String cardNumber;
    private String cardCode;
    private double balance;
    private int reward;
    private boolean activated;
    private String status;

 

}