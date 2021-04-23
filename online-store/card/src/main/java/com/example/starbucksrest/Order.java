package com.example.starbucksrest;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "Starbucks_Order")
@Data
@RequiredArgsConstructor
class Order{

	private @Id @GeneratedValue Long id;

    @Column(nullable = false)
    private String drink;
    @Column(nullable = false)
    private String milk;
    @Column(nullable = false)
    private String size;
    @Column(nullable = false)
    private double total;
    private String status;


    enum DrinkType{
        LATTE, AMERICANO, MOCHA;
    }
   
}
