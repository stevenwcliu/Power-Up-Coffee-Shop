package com.example.springcashier;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Digits; 
import javax.validation.constraints.Pattern; 
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
class Payment {

    @NotNull
    @Size(min=1, message="Name must be at least 1 characters long")
    private String name;

    @NotNull( message="Order Number is required field!")
    @Pattern(regexp="^[0-9]*$",message="Must be a valid order number!")
    private String orderNumber;

    @NotNull( message="Card Number is required field!")
    @Pattern(regexp="^[0-9]{9}",message="Must be a 9 digit valid card number!")
    private String cardNumber;

}