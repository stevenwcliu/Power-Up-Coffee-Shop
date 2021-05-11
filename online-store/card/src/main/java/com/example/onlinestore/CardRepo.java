package com.example.onlinestore;

import java.util.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.onlinestore.StarbucksCard;

public interface CardRepo extends CrudRepository<StarbucksCard, Integer> {

	@Query(value = "SELECT card FROM StarbucksCard card")
	List<StarbucksCard> findAllCard();

	@Query(value = "SELECT s.rewards FROM StarbucksCard s WHERE s.nickName= ?1 ")
	StarbucksCard findRewards(String nickName);

}