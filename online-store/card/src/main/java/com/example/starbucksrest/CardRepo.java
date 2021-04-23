package com.example.starbucksrest;

import java.util.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.starbucksrest.StarbucksCard;

public interface CardRepo extends CrudRepository<StarbucksCard, Integer> {

	@Query(value = "SELECT card from StarbucksCard card")
	List<StarbucksCard> findAllCard();

	StarbucksCard findByCardNumber(String cardNumber);
}