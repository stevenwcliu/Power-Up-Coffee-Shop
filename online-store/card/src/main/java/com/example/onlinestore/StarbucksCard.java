package com.example.onlinestore;

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
@Table(indexes=@Index(name = "altIndex", columnList = "cardNumber", unique = true))
@Data
@RequiredArgsConstructor
class StarbucksCard {

	private @Id @GeneratedValue Long id;
	 
    private String name;
    private String cardNumber ;
     
    private String cardCode;
    
    private double balance;
   
    private boolean activated;
    private String status;
    
    private String rewards;

    public boolean isActivated(){
        return activated = true;
    }
}
