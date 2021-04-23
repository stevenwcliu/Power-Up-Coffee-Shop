package com.example.starbucksapi;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.Data;
import lombok.RequiredArgsConstructor;



@Data
class StarbucksGetOrder {

    private String Drink;
    private String Milk;
    private String Size;

    public StarbucksGetOrder (String Drink, String Milk, String Size) {
        this.Drink = Drink;
        this.Milk = Milk;
        this.Size = Size;
    }

}