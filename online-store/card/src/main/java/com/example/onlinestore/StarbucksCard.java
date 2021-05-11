package com.example.onlinestore;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "New Card")
@Data
@RequiredArgsConstructor
class StarbucksCard {

	private @Id @GeneratedValue Long id;
	
    @NotNull
    private String nickName;

    @NotNull
    private String cardType;

    @NotNull
    private String firstname;

    @NotNull
    private String lastname;

    @NotNull
    private String expYear ;

    @NotNull
    private String expMon ;

    @NotNull
    private String cardNum ;

    @NotNull
    private String cvv;
    
    @NotNull
    private double balance;
   
    @NotNull
    private Integer rewards;
}
