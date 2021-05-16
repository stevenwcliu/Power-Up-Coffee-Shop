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


@Data
@RequiredArgsConstructor
class PaymentInfo {

	private Long id;
	
 
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
    private String address;
    @NotNull
    private String city;
    @NotNull
    private String state;
    @NotNull(message = "Please enter 5 digits zipcode")
    // @Pattern(regexp = "^[0-9]{5}")
    private String zip ;
    @NotNull
    // @Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$")
    private String phonenumber ;
    @NotNull
    private String email;
    
    
    private double balance; 
   
    
   
}
