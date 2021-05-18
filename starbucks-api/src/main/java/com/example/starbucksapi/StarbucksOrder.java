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

@Entity
@Table
@Data
@RequiredArgsConstructor
class StarbucksOrder {

    private @Id @GeneratedValue Long id;
    private String drink;
    private String milk;
    private String size;
    private double total;
    private String status;

}