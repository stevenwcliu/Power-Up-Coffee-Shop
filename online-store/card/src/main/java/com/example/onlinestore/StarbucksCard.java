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

@Entity
@Table(indexes=@Index(name = "altIndex", columnList = "cardNumber", unique = true))
@Data
@RequiredArgsConstructor
class StarbucksCard {

    private @Id @GeneratedValue Long id;

    @Column(nullable=false) private String cardNumber;
    @Column(nullable=false) private String cardCode;
    @Column(nullable=false) private double balance;
    @Column(nullable=false) private Integer rewards;
    @Column(nullable=false) private boolean activated;
                            private String status;

    // public boolean isActivated(){
    //     return activated = true;
    // }

}