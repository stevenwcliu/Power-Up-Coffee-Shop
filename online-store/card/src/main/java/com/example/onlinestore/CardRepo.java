package com.example.onlinestore;

import java.util.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.onlinestore.StarbucksCard;

public interface CardRepo extends CrudRepository<StarbucksCard, Integer> {

	@Query(value = "SELECT card from StarbucksCard card")
	List<StarbucksCard> findAllCard();

	StarbucksCard findByCardNumber(String cardNumber);
}